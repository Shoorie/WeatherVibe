package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.sharing.ShareBitmapAsImage
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.InvalidateAndRegenerate
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.ReformatOnly
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.RegenerateSuggestion
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.feature.home.presentation.HomeAction.GenreRemoveClick
import com.weather.vibe.feature.home.presentation.HomeAction.Initialize
import com.weather.vibe.feature.home.presentation.HomeAction.PosterCaptured
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.RetryWeatherSuggestion
import com.weather.vibe.feature.home.presentation.HomeAction.ShareClick
import com.weather.vibe.feature.home.presentation.HomeEvent.SharePoster
import com.weather.vibe.feature.home.presentation.factory.HomeStateFactory
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
  private val resources: HomeResources,
  private val shareBitmapAsImage: ShareBitmapAsImage,
  private val stateFactory: HomeStateFactory,
  private val useCases: HomeUseCases,
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  private val _event = Channel<HomeEvent>(capacity = BUFFERED)
  val event: Flow<HomeEvent> = _event.receiveAsFlow()

  private var snapshot = HomeSnapshot()
  private var currentCoordinates: Coordinates? = null
  private var currentSettings: UserSettings? = null
  private var homeDataJob: Job? = null
  private var suggestionJob: Job? = null
  private var invalidateJob: Job? = null
  private var genreJob: Job? = null
  private var vibeJob: Job? = null

  private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    if (throwable is CancellationException) return@CoroutineExceptionHandler
    showError(throwable)
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is GenreRemoveClick -> onGenreRemoveClick(action)
      is Initialize -> onInitialize(action)
      is PosterCaptured -> onPosterCaptured(action)
      is RefreshClick -> onRefreshClick()
      is RetryWeatherSuggestion -> onRetryWeatherSuggestion()
      is ShareClick -> onShareClick()
    }
  }

  private fun onInitialize(action: Initialize) {
    viewModelScope.launch(errorHandler) {
      val coordinates = useCases.getStartingCoordinates(action.location)
      if (isAlreadyShowing(coordinates)) return@launch
      observeWeather(coordinates)
    }
  }

  private fun isAlreadyShowing(coordinates: Coordinates): Boolean {
    if (_state.value !is Loaded) return false
    return snapshot.weatherData?.coordinates == coordinates
  }

  private fun observeWeather(coordinates: Coordinates) {
    currentCoordinates = coordinates
    _state.update { Loading }
    snapshot = HomeSnapshot()
    cancelInFlightJobs()
    launchHomeDataObservation(coordinates)
  }

  private fun cancelInFlightJobs() {
    vibeJob?.cancel()
    suggestionJob?.cancel()
    invalidateJob?.cancel()
    genreJob?.cancel()
  }

  private fun refreshWeather(coordinates: Coordinates) {
    launchHomeDataObservation(coordinates)
  }

  private fun launchHomeDataObservation(coordinates: Coordinates) {
    homeDataJob?.cancel()
    homeDataJob = combine(
      useCases.getWeather(coordinates),
      useCases.observeUserSettings(),
      ::onHomeDataResult
    ).launchIn(viewModelScope)
  }

  private fun onHomeDataResult(
    weatherResult: Result<WeatherData>,
    settingsResult: Result<UserSettings>
  ) {
    settingsResult.fold(
      onSuccess = { settings -> onSettingsReady(weatherResult, settings) },
      onFailure = ::showError
    )
  }

  private fun onSettingsReady(weatherResult: Result<WeatherData>, settings: UserSettings) {

    val previousSettings = currentSettings
    currentSettings = settings

    weatherResult.fold(
      onSuccess = { weather -> onWeatherReady(weather, settings, previousSettings) },
      onFailure = ::showError
    )
  }

  private fun onWeatherReady(
    weather: WeatherData,
    settings: UserSettings,
    previousSettings: UserSettings?
  ) {

    val previousWeatherKey = snapshot.weatherKey
    val weatherKey = useCases.getCurrentWeatherKey(weather)
    snapshot = snapshot.copy(weatherData = weather, weatherKey = weatherKey)

    val strategy = useCases.determineWeatherRefreshStrategy(
      previousWeatherKey = previousWeatherKey,
      currentWeatherKey = weatherKey,
      previousSettings = previousSettings,
      currentSettings = settings
    )

    when (strategy) {
      RegenerateSuggestion -> onRegenerateSuggestion(weather, settings)
      InvalidateAndRegenerate -> onInvalidateAndRegenerateSuggestion(weather, settings, weatherKey)
      ReformatOnly -> onTemperaturesReformatted(weather, settings)
    }
  }

  private fun onRegenerateSuggestion(weather: WeatherData, settings: UserSettings) {
    showWeatherLoaded(weather, settings)
    refreshWeatherSuggestion()
  }

  private fun onInvalidateAndRegenerateSuggestion(
    weather: WeatherData,
    settings: UserSettings,
    weatherKey: WeatherKey
  ) {

    showWeatherLoaded(weather, settings)

    invalidateJob?.cancel()
    invalidateJob = viewModelScope.launch(errorHandler) {
      useCases.invalidateWeatherSuggestion(
        tone = settings.briefTone,
        weatherKey = weatherKey
      )
      refreshWeatherSuggestion()
    }
  }

  private fun showWeatherLoaded(weather: WeatherData, settings: UserSettings) {
    _state.update { current ->
      val previousVibe = (current as? Loaded)?.dailyVibe
      stateFactory.create(
        data = weather,
        unit = settings.temperatureUnit
      ).copy(dailyVibe = previousVibe)
    }
    refreshDailyVibe(weather)
  }

  private fun refreshDailyVibe(weather: WeatherData) {
    vibeJob?.cancel()
    vibeJob = viewModelScope.launch(errorHandler) {

      val vibe = useCases.calculateDailyVibe(weather)
        .getOrNull() ?: return@launch

      val vibeState = stateFactory.createDailyVibe(vibe)

      ensureActive()
      _state.update { stateFactory.applyDailyVibe(it, vibeState) }
    }
  }

  private fun onTemperaturesReformatted(weather: WeatherData, settings: UserSettings) {
    _state.update {
      stateFactory.reformatTemperatures(
        current = it,
        data = weather,
        unit = settings.temperatureUnit
      )
    }
  }

  private fun showError(error: Throwable) {
    _state.update { HomeUiState.Error(error.message ?: resources.defaultError()) }
  }

  private fun onRefreshClick() {

    val coordinates = currentCoordinates ?: return
    val current = _state.value as? Loaded

    if (current != null) {
      _state.update { current.copy(isRefreshing = true) }
      refreshWeather(coordinates)
    } else {
      observeWeather(coordinates)
    }
  }

  private fun onRetryWeatherSuggestion() {
    refreshWeatherSuggestion()
  }

  private fun onShareClick() {

    val weather = snapshot.weatherData ?: return
    val suggestion = snapshot.weatherSuggestion ?: return
    val unit = currentSettings?.temperatureUnit ?: return
    val vibeOneLiner = (_state.value as? Loaded)?.dailyVibe?.oneLiner
    val poster = stateFactory.createSharePoster(
      suggestion = suggestion,
      vibeOneLiner = vibeOneLiner,
      weather = weather,
      unit = unit
    )

    send(SharePoster(poster))
  }

  private fun onPosterCaptured(action: PosterCaptured) {
    viewModelScope.launch(errorHandler) {
      shareBitmapAsImage(
        bitmap = action.bitmap,
        chooserTitle = resources.shareChooserTitle()
      )
    }
  }

  private fun onGenreRemoveClick(action: GenreRemoveClick) {

    val tone = currentSettings?.briefTone ?: return

    genreJob?.cancel()
    genreJob = viewModelScope.launch(errorHandler) {

      withContext(NonCancellable) { useCases.excludeGenre(action.genre) }

      _state.update { stateFactory.markGenreAsRejecting(it, action.genre) }

      if (stateFactory.areAllGenresRejected(_state.value)) {
        onAllGenresRejected(tone)
      }
    }
  }

  private suspend fun onAllGenresRejected(tone: BriefTone) {

    showPlaylistGenerating()

    val weatherKey = snapshot.weatherKey ?: return
    useCases.invalidateWeatherSuggestion(tone = tone, weatherKey = weatherKey)
    refreshWeatherSuggestion()
  }

  private fun showPlaylistGenerating() {
    _state.update {
      stateFactory.applyPlaylist(
        current = it,
        playlist = Generating(resources.findingBetterSuggestions())
      )
    }
  }

  private fun refreshWeatherSuggestion() {

    val weatherData = snapshot.weatherData ?: return
    val weatherKey = snapshot.weatherKey ?: return

    suggestionJob?.cancel()
    suggestionJob = useCases.generateWeatherSuggestion(weatherData, weatherKey)
      .onEach(::onWeatherSuggestionResult)
      .launchIn(viewModelScope)
  }

  private fun onWeatherSuggestionResult(result: Result<WeatherSuggestion>) {
    result.fold(
      onSuccess = ::onWeatherSuggestionSuccess,
      onFailure = { onWeatherSuggestionError() }
    )
  }

  private fun onWeatherSuggestionSuccess(suggestion: WeatherSuggestion) {
    snapshot = snapshot.copy(weatherSuggestion = suggestion)
    showWeatherSuggestion(
      briefing = stateFactory.createBriefing(suggestion = suggestion),
      playlist = stateFactory.createPlaylist(suggestion = suggestion)
    )
  }

  private fun onWeatherSuggestionError() {
    showWeatherSuggestion(
      briefing = BriefingUiState.Error(canRetry = true),
      playlist = PlaylistUiState.Error
    )
  }

  private fun showWeatherSuggestion(
    briefing: BriefingUiState,
    playlist: PlaylistUiState
  ) {
    _state.update {
      stateFactory.applyWeatherSuggestion(
        briefing = briefing,
        current = it,
        playlist = playlist
      )
    }
  }

  private fun send(event: HomeEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
