package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.model.toCoordinates
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
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.ResumeLifecycle
import com.weather.vibe.feature.home.presentation.HomeAction.RetryWeatherSuggestion
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
  private val resources: HomeResources,
  private val stateFactory: HomeStateFactory,
  private val useCases: HomeUseCases,
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  private var snapshot = HomeSnapshot()
  private var currentSettings: UserSettings? = null
  private var homeDataJob: Job? = null
  private var suggestionJob: Job? = null
  private var settingsJob: Job? = null

  private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    showError(throwable)
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is GenreRemoveClick -> onGenreRemoveClick(action)
      is Initialize -> onInitialize(action)
      is RefreshClick -> onRefreshClick()
      is RetryWeatherSuggestion -> onRetryWeatherSuggestion()
      is ResumeLifecycle -> onResumeLifecycle()
    }
  }

  private fun onInitialize(action: Initialize) {
    val coordinates = action.location?.toCoordinates() ?: DEFAULT_LOCATION
    observeWeather(coordinates)
  }

  private fun observeWeather(coordinates: Coordinates = DEFAULT_LOCATION) {

    _state.update { Loading }
    snapshot = HomeSnapshot()

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

    settingsJob?.cancel()
    settingsJob = viewModelScope.launch {
      useCases.invalidateWeatherSuggestion(
        tone = settings.briefTone,
        weatherKey = weatherKey
      )
      refreshWeatherSuggestion()
    }
  }

  private fun showWeatherLoaded(weather: WeatherData, settings: UserSettings) {
    _state.update { stateFactory.create(data = weather, unit = settings.temperatureUnit) }
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
    observeWeather()
  }

  private fun onRetryWeatherSuggestion() {
    refreshWeatherSuggestion()
  }

  private fun onResumeLifecycle() {
    if (suggestionJob?.isActive == true) return
    if (_state.value.isPlaylistLoaded) return
    refreshWeatherSuggestion()
  }

  private fun onGenreRemoveClick(action: GenreRemoveClick) {

    val tone = currentSettings?.briefTone ?: return
    val updatedState = _state.updateAndGet { it.withGenreRejecting(action.genre) }

    settingsJob?.cancel()
    settingsJob = viewModelScope.launch(errorHandler) {

      useCases.excludeGenre(action.genre)

      if (updatedState.allGenresRejected) {
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
    _state.update { it.withPlaylist(Generating(resources.findingBetterSuggestions())) }
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
    showWeatherSuggestion(
      briefing = BriefingUiState.Loaded(text = suggestion.briefText),
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

  private companion object {
    val DEFAULT_LOCATION = Coordinates(
      name = "Toruń",
      latitude = 53.0138,
      longitude = 18.5984
    )
  }
}
