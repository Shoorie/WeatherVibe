package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.sharing.ShareBitmapAsImage
import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.viberating.mapper.WeatherDataToVibeSnapshot
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.UserDispositionEntry
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
import com.weather.vibe.feature.home.presentation.factory.HomeFactories
import com.weather.vibe.feature.home.presentation.factory.HomeStateFactory
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.DailyVibeCardUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.presentation.state.rejectGenre
import com.weather.vibe.feature.home.presentation.state.withAlert
import com.weather.vibe.feature.home.presentation.state.withDailyVibe
import com.weather.vibe.feature.home.presentation.state.withPlaylist
import com.weather.vibe.feature.home.presentation.state.withSuggestion
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
  private val factories: HomeFactories,
  private val resources: HomeResources,
  private val shareBitmapAsImage: ShareBitmapAsImage,
  private val stateFactory: HomeStateFactory,
  private val useCases: HomeUseCases,
  private val weatherDataToVibeSnapshot: WeatherDataToVibeSnapshot
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  private val _event = Channel<HomeEvent>(capacity = BUFFERED)
  val event: Flow<HomeEvent> = _event.receiveAsFlow()

  private val snapshot = MutableStateFlow(HomeSnapshot())
  private var currentCoordinates: Coordinates? = null
  private var currentSettings: UserSettings? = null
  private var homeDataJob: Job? = null
  private var suggestionJob: Job? = null
  private var invalidateJob: Job? = null
  private var genreRejectionJob: Job? = null
  private var dailyVibeJob: Job? = null
  private var posterShareJob: Job? = null

  private val errorHandler = CoroutineExceptionHandler { _, throwable -> showError(throwable) }

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
    val coordinates = action.location.toCoordinates()
    if (isAlreadyShowing(coordinates)) return
    observeWeather(coordinates)
  }

  private fun isAlreadyShowing(coordinates: Coordinates): Boolean {
    if (_state.value !is Loaded) return false
    return snapshot.value.weatherData?.coordinates == coordinates
  }

  private fun observeWeather(coordinates: Coordinates) {
    currentCoordinates = coordinates
    _state.update { Loading }
    snapshot.update { HomeSnapshot() }
    cancelDerivedJobs()
    launchHomeDataObservation(coordinates)
  }

  private fun cancelDerivedJobs() {
    dailyVibeJob?.cancel()
    suggestionJob?.cancel()
    invalidateJob?.cancel()
    genreRejectionJob?.cancel()
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

  private fun onSettingsReady(
    weatherResult: Result<WeatherData>,
    settings: UserSettings
  ) {

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

    val previousWeatherKey = snapshot.value.weatherKey
    val weatherKey = useCases.getCurrentWeatherKey(weather)
    snapshot.update { it.copy(weatherData = weather, weatherKey = weatherKey) }

    val strategy = useCases.determineWeatherRefreshStrategy(
      previousWeatherKey = previousWeatherKey,
      currentWeatherKey = weatherKey,
      previousSettings = previousSettings,
      currentSettings = settings
    )

    when (strategy) {
      RegenerateSuggestion -> onRegenerateSuggestion(weather, settings)
      InvalidateAndRegenerate ->
        onInvalidateAndRegenerateSuggestion(weather, settings, weatherKey)
      ReformatOnly -> onReformatOnly(weather, settings)
    }
    clearRefreshFlag()
  }

  private fun clearRefreshFlag() {
    _state.update { current ->
      (current as? Loaded)?.copy(isRefreshing = false) ?: current
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

    suggestionJob?.cancel()
    invalidateJob?.cancel()
    invalidateJob = viewModelScope.launch(errorHandler) {
      useCases.invalidateWeatherSuggestion(
        locationId = weather.coordinates.id,
        todayDispositionEntries = currentDispositionEntries(),
        tone = settings.briefTone,
        weatherKey = weatherKey
      )
      ensureActive()
      refreshWeatherSuggestion()
    }
  }

  private fun onReformatOnly(weather: WeatherData, settings: UserSettings) {
    val metrics = useCases.getCurrentWeatherMetrics(weather)
    _state.update {
      stateFactory.reformatTemperatures(
        current = it,
        data = weather,
        metrics = metrics,
        unit = settings.temperatureUnit
      )
    }
  }

  private fun showWeatherLoaded(weather: WeatherData, settings: UserSettings) {
    _state.update { rebuildLoadedState(weather, settings) }
    refreshDailyVibe(weather, settings)
  }

  private fun rebuildLoadedState(weather: WeatherData, settings: UserSettings): Loaded {
    val base = stateFactory.create(
      data = weather,
      metrics = useCases.getCurrentWeatherMetrics(weather),
      vibeSnapshot = weatherDataToVibeSnapshot.map(weather),
      unit = settings.temperatureUnit
    )
    return base.copy(dailyVibe = preservedDailyVibeCard())
  }

  private fun preservedDailyVibeCard(): DailyVibeCardUiState? {
    val vibe = snapshot.value.dailyVibe ?: return null
    val readings = snapshot.value.readings
    return DailyVibeCardUiState(
      airQualityChip = factories.environment.buildAirQualityChip(readings),
      pollenChip = factories.environment.buildPollenChip(readings),
      vibe = factories.aiSuggestion.buildDailyVibe(vibe)
    )
  }

  private fun refreshDailyVibe(weather: WeatherData, settings: UserSettings) {
    dailyVibeJob?.cancel()
    dailyVibeJob = viewModelScope.launch(errorHandler) {
      val readings = fetchEnvironmentReadings(weather.coordinates) ?: return@launch
      ensureActive()
      updateAlert(readings, settings)
      ensureActive()
      updateDailyVibeCard(weather, readings)
    }
  }

  private suspend fun fetchEnvironmentReadings(
    coordinates: Coordinates
  ): EnvironmentalReadings? {
    val readings = useCases.getEnvironmentalReadings(coordinates)
    if (currentCoordinates != coordinates) return null
    snapshot.update { it.copy(readings = readings) }
    return readings
  }

  private fun updateAlert(
    environmentReadings: EnvironmentalReadings,
    settings: UserSettings
  ) {
    val alertModel = useCases.resolveHomeAlert(
      pollenAlertsEnabled = settings.pollenAlertsEnabled,
      readings = environmentReadings,
      weatherAlertsEnabled = settings.weatherAlertsEnabled
    )
    val alertState = factories.environment.buildAlert(alertModel)
    _state.update { it.withAlert(alertState) }
  }

  private fun updateDailyVibeCard(
    weather: WeatherData,
    environmentReadings: EnvironmentalReadings
  ) {
    val vibe = useCases.calculateDailyVibe(weather, environmentReadings).getOrNull() ?: return
    snapshot.update { it.copy(dailyVibe = vibe) }
    val card = DailyVibeCardUiState(
      airQualityChip = factories.environment.buildAirQualityChip(environmentReadings),
      pollenChip = factories.environment.buildPollenChip(environmentReadings),
      vibe = factories.aiSuggestion.buildDailyVibe(vibe)
    )
    _state.update { it.withDailyVibe(card) }
  }

  private fun refreshWeatherSuggestion() {

    val weatherData = snapshot.value.weatherData ?: return
    val weatherKey = snapshot.value.weatherKey ?: return

    suggestionJob?.cancel()
    suggestionJob = viewModelScope.launch(errorHandler) {
      val entries = currentDispositionEntries()
      useCases.generateWeatherSuggestion(
        todayDispositionEntries = entries,
        weatherData = weatherData,
        weatherKey = weatherKey
      ).collect(::onWeatherSuggestionResult)
    }
  }

  private suspend fun currentDispositionEntries(): List<UserDispositionEntry> =
    useCases.observeTodayEntries().first().toDispositionEntries()

  private fun onWeatherSuggestionResult(result: Result<WeatherSuggestion>) {
    result.fold(
      onSuccess = ::onWeatherSuggestionSuccess,
      onFailure = { onWeatherSuggestionError() }
    )
  }

  private fun onWeatherSuggestionSuccess(suggestion: WeatherSuggestion) {
    snapshot.update {
      it.copy(weatherSuggestion = suggestion, rejectedGenres = emptySet())
    }
    showWeatherSuggestion(
      briefing = factories.aiSuggestion.buildBriefing(suggestion),
      playlist = factories.aiSuggestion.buildPlaylist(suggestion)
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
    _state.update { it.withSuggestion(briefing = briefing, playlist = playlist) }
  }

  private fun onGenreRemoveClick(action: GenreRemoveClick) {

    val tone = currentSettings?.briefTone ?: return

    persistGenreExclusion(action.genre)
    snapshot.update { it.copy(rejectedGenres = it.rejectedGenres + action.genre) }
    _state.update { it.rejectGenre(action.genre) }

    if (allCurrentGenresRejected()) {
      regenerateSuggestion(tone)
    }
  }

  private fun persistGenreExclusion(genre: String) {
    viewModelScope.launch(errorHandler) { useCases.excludeGenre(genre) }
  }

  private fun allCurrentGenresRejected(): Boolean {
    val genres = snapshot.value.weatherSuggestion?.genres ?: return false
    val rejected = snapshot.value.rejectedGenres
    return genres.isNotEmpty() && genres.all { it in rejected }
  }

  private fun regenerateSuggestion(tone: BriefTone) {
    val weatherKey = snapshot.value.weatherKey ?: return
    val locationId = snapshot.value.weatherData?.coordinates?.id ?: return
    genreRejectionJob?.cancel()
    genreRejectionJob = viewModelScope.launch(errorHandler) {
      showPlaylistGenerating()
      useCases.invalidateWeatherSuggestion(
        locationId = locationId,
        todayDispositionEntries = currentDispositionEntries(),
        tone = tone,
        weatherKey = weatherKey
      )
      ensureActive()
      refreshWeatherSuggestion()
    }
  }

  private fun showPlaylistGenerating() {
    _state.update {
      val status = Generating(resources.findingBetterSuggestions())
      it.withPlaylist(status)
    }
  }

  private fun onShareClick() {

    val weather = snapshot.value.weatherData ?: return
    val suggestion = snapshot.value.weatherSuggestion ?: return
    val unit = currentSettings?.temperatureUnit ?: return
    val vibeOneLiner = snapshot.value.dailyVibe?.let { resources.dailyVibeOneLiner(it.mood) }

    val poster = factories.sharePoster.create(
      suggestion = suggestion,
      unit = unit,
      vibeOneLiner = vibeOneLiner,
      weather = weather
    )
    send(SharePoster(poster))
  }

  private fun onPosterCaptured(action: PosterCaptured) {
    posterShareJob?.cancel()
    posterShareJob = viewModelScope.launch(errorHandler) {
      shareBitmapAsImage(
        bitmap = action.bitmap,
        chooserTitle = resources.shareChooserTitle()
      )
    }
  }

  private fun onRefreshClick() {

    val coordinates = currentCoordinates ?: return
    val current = _state.value as? Loaded

    if (current != null) {
      _state.update { current.copy(isRefreshing = true) }
      cancelDerivedJobs()
      launchHomeDataObservation(coordinates)
    } else {
      observeWeather(coordinates)
    }
  }

  private fun onRetryWeatherSuggestion() {
    refreshWeatherSuggestion()
  }

  private fun showError(error: Throwable) {
    _state.update {
      HomeUiState.Error(error.message ?: resources.defaultError())
    }
  }

  private fun send(event: HomeEvent) {
    viewModelScope.launch(errorHandler) { _event.send(event) }
  }
}
