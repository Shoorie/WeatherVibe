package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SaveUserSettings
import com.weather.vibe.domain.weather.model.AiSuggestion
import com.weather.vibe.domain.weather.model.Location
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.ComputeWeatherKey
import com.weather.vibe.domain.weather.usecase.GenerateAiSuggestion
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.feature.home.presentation.HomeAction.GenreRemoveClick
import com.weather.vibe.feature.home.presentation.HomeAction.ReceiveLocationResult
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.ResumeLifecycle
import com.weather.vibe.feature.home.presentation.HomeAction.RetryAiContent
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalTime

@KoinViewModel
internal class HomeViewModel(
  private val computeWeatherKey: ComputeWeatherKey,
  private val generateAiSuggestion: GenerateAiSuggestion,
  private val observeUserSettings: ObserveUserSettings,
  private val getWeather: GetWeather,
  private val resources: HomeResources,
  private val saveUserSettings: SaveUserSettings,
  private val stateFactory: HomeStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  private var snapshot = HomeSnapshot()
  private var currentSettings: UserSettings? = null
  private var homeDataJob: Job? = null
  private var aiJob: Job? = null

  init {
    startObserving()
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is GenreRemoveClick -> onGenreRemoveClick(action)
      is ReceiveLocationResult -> onReceiveLocationResult(action)
      is RefreshClick -> onRefreshClick()
      is RetryAiContent -> onRetryAiContent()
      is ResumeLifecycle -> onResumeLifecycle()
    }
  }

  private fun startObserving(location: Location = defaultLocation()) {
    _state.update { Loading }
    homeDataJob?.cancel()
    homeDataJob = combine(
      getWeather(location),
      observeUserSettings()
    ) { weatherResult, settingsResult -> weatherResult to settingsResult }
      .onEach(::onHomeDataResult)
      .launchIn(viewModelScope)
  }

  private fun onHomeDataResult(results: Pair<Result<WeatherData>, Result<UserSettings>>) {

    val (weatherResult, settingsResult) = results
    val settings = settingsResult.getOrNull() ?: return
    val previousSettings = currentSettings
    currentSettings = settings

    weatherResult.fold(
      onSuccess = { weather -> onWeatherReady(weather, settings, previousSettings) },
      onFailure = ::onWeatherError
    )
  }

  private fun onWeatherReady(
    weather: WeatherData,
    settings: UserSettings,
    previousSettings: UserSettings?
  ) {

    val weatherKey = computeWeatherKey(
      condition = weather.condition,
      hour = LocalTime.now().hour,
      temperatureCelsius = weather.currentTemperature
    )
    val weatherConditionsChanged = weatherKey != snapshot.weatherKey
    snapshot = snapshot.copy(weatherData = weather, weatherKey = weatherKey)

    if (aiShouldRefresh(weatherConditionsChanged, settings, previousSettings)) {
      _state.update { stateFactory.create(weather, settings.temperatureUnit) }
      refreshAiContent()
    } else {
      _state.update { stateFactory.reformatTemperatures(it, weather, settings.temperatureUnit) }
    }
  }

  private fun aiShouldRefresh(
    weatherConditionsChanged: Boolean,
    settings: UserSettings,
    previousSettings: UserSettings?
  ): Boolean = _state.value !is HomeUiState.Loaded
    || weatherConditionsChanged
    || previousSettings != null && settings.hasAiRelevantChange(previousSettings)

  private fun onWeatherError(error: Throwable) {
    _state.update { HomeUiState.Error(error.message ?: resources.defaultError()) }
  }

  private fun onRefreshClick() {
    startObserving()
  }

  private fun onRetryAiContent() {
    refreshAiContent()
  }

  private fun onResumeLifecycle() {
    if (aiJob?.isActive == true) return
    if (_state.value.isPlaylistLoaded) return
    refreshAiContent()
  }

  private fun onReceiveLocationResult(action: ReceiveLocationResult) {
    val location = Location(
      cityName = action.cityName,
      latitude = action.latitude,
      longitude = action.longitude
    )
    startObserving(location)
  }

  private fun onGenreRemoveClick(action: GenreRemoveClick) {
    val settings = currentSettings ?: return
    val updatedSettings = settings.withExcludedGenres(settings.excludedGenres + action.genre)

    _state.update { it.withGenreRejecting(action.genre) }

    if (_state.value.allGenresRejected) {
      onAllGenresRejected(updatedSettings)
    } else {
      viewModelScope.launch { saveUserSettings(updatedSettings) }
    }
  }

  private fun onAllGenresRejected(settings: UserSettings) {
    _state.update { it.withPlaylist(PlaylistUiState.Generating(resources.findingBetterSuggestions())) }
    viewModelScope.launch {
      saveUserSettings(settings = settings)
      refreshAiContent()
    }
  }

  private fun refreshAiContent() {

    val weatherData = snapshot.weatherData ?: return
    val weatherKey = snapshot.weatherKey ?: return

    aiJob?.cancel()
    aiJob = generateAiSuggestion(weatherData = weatherData, weatherKey = weatherKey)
      .onEach { result ->
        result.fold(
          onSuccess = ::onAiContentSuccess,
          onFailure = { onAiContentError() }
        )
      }
      .launchIn(viewModelScope)
  }

  private fun onAiContentSuccess(suggestion: AiSuggestion) {
    _state.update {
      stateFactory.applyAiContent(
        briefing = BriefingUiState.Loaded(text = suggestion.briefText),
        current = it,
        playlist = stateFactory.createPlaylist(suggestion = suggestion)
      )
    }
  }

  private fun onAiContentError() {
    _state.update {
      stateFactory.applyAiContent(
        briefing = BriefingUiState.Error(canRetry = true),
        current = it,
        playlist = PlaylistUiState.Error
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
