package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.weather.model.Location
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.feature.home.presentation.HomeAction.GenreRemoveClick
import com.weather.vibe.feature.home.presentation.HomeAction.ReceiveLocationResult
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.ResumeLifecycle
import com.weather.vibe.feature.home.presentation.HomeAction.RetryWeatherSuggestion
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.ui.HomeResources
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
  private val timeProvider: TimeProvider,
  private val useCases: HomeUseCases,
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  private var snapshot = HomeSnapshot()
  private var currentSettings: UserSettings? = null
  private var homeDataJob: Job? = null
  private var suggestionJob: Job? = null
  private var settingsJob: Job? = null

  init {
    observeWeather()
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is GenreRemoveClick -> onGenreRemoveClick(action)
      is ReceiveLocationResult -> onReceiveLocationResult(action)
      is RefreshClick -> onRefreshClick()
      is RetryWeatherSuggestion -> onRetryWeatherSuggestion()
      is ResumeLifecycle -> onResumeLifecycle()
    }
  }

  private fun observeWeather(location: Location = defaultLocation()) {

    _state.update { Loading }

    homeDataJob?.cancel()
    homeDataJob = combine(
      useCases.getWeather(location),
      useCases.observeUserSettings()
    ) { weatherResult, settingsResult -> weatherResult to settingsResult }
      .onEach(::onHomeDataResult)
      .launchIn(viewModelScope)
  }

  private fun onHomeDataResult(results: Pair<Result<WeatherData>, Result<UserSettings>>) {
    val (weatherResult, settingsResult) = results
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
    val weatherKey = useCases.computeWeatherKey(
      condition = weather.condition,
      hour = timeProvider.now().hour,
      temperatureCelsius = weather.currentTemperature
    )
    snapshot = snapshot.copy(weatherData = weather, weatherKey = weatherKey)

    val isFirstLoad = _state.value !is Loaded
    val weatherChanged = weatherKey != previousWeatherKey
    val toneChanged = settings.hasBriefToneChanged(previousSettings)

    when {
      isFirstLoad || weatherChanged -> onWeatherChanged(weather, settings)
      toneChanged -> onBriefToneChanged(weather, settings, weatherKey)
      else -> showTemperaturesReformatted(weather, settings)
    }
  }

  private fun onWeatherChanged(weather: WeatherData, settings: UserSettings) {
    showWeatherLoaded(weather, settings)
    refreshWeatherSuggestion()
  }

  private fun onBriefToneChanged(
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
    _state.update {
      stateFactory.create(
        data = weather,
        temperatureUnit = settings.temperatureUnit
      )
    }
  }

  private fun showTemperaturesReformatted(weather: WeatherData, settings: UserSettings) {
    _state.update {
      stateFactory.reformatTemperatures(
        current = it,
        data = weather,
        temperatureUnit = settings.temperatureUnit
      )
    }
  }

  private fun showError(error: Throwable) {
    _state.update {
      HomeUiState.Error(
        error.message ?: resources.defaultError()
      )
    }
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

  private fun onReceiveLocationResult(action: ReceiveLocationResult) {
    val location = Location(
      cityName = action.cityName,
      latitude = action.latitude,
      longitude = action.longitude
    )
    observeWeather(location)
  }

  private fun onGenreRemoveClick(action: GenreRemoveClick) {

    val settings = currentSettings ?: return
    val updatedSettings = settings
      .withExcludedGenres(settings.excludedGenres + action.genre)

    val updatedState = _state.updateAndGet { it.withGenreRejecting(action.genre) }

    when (updatedState.allGenresRejected) {
      true -> onAllGenresRejected(updatedSettings)
      false -> saveSettings(updatedSettings)
    }
  }

  private fun saveSettings(settings: UserSettings) {
    settingsJob?.cancel()
    settingsJob = viewModelScope.launch {
      useCases.saveUserSettings(settings)
    }
  }

  private fun onAllGenresRejected(settings: UserSettings) {

    showPlaylistGenerating()

    settingsJob?.cancel()
    settingsJob = viewModelScope.launch {
      useCases.saveUserSettings(settings = settings)
      useCases.invalidateWeatherSuggestion(
        tone = settings.briefTone,
        weatherKey = snapshot.weatherKey ?: return@launch
      )
      refreshWeatherSuggestion()
    }
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
    fun defaultLocation(): Location =
      Location(
        cityName = "Toruń",
        latitude = 53.0138,
        longitude = 18.5984
      )
  }
}
