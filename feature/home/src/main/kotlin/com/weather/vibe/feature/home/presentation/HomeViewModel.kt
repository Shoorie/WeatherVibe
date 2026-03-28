package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.usecase.GetUserSettings
import com.weather.vibe.domain.weather.model.WeatherAiContent
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GenerateWeatherAiContent
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.feature.home.presentation.HomeAction.ReceiveLocationResult
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.ResumeLifecycle
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
  private val generateWeatherAiContent: GenerateWeatherAiContent,
  private val getUserSettings: GetUserSettings,
  private val getWeather: GetWeather,
  private val resources: HomeResources,
  private val stateFactory: HomeStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  private val snapshot = MutableStateFlow(HomeSnapshot())

  init {
    getUserSettings()
      .onEach(::onSettingsResult)
      .launchIn(viewModelScope)
    loadWeather()
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is ReceiveLocationResult -> onReceiveLocationResult(action)
      is RefreshClick -> onRefreshClick()
      is ResumeLifecycle -> onResumeLifecycle()
    }
  }

  private fun onSettingsResult(result: Result<UserSettings>) {
    result.onSuccess(::onSettingsUpdate)
  }

  private fun onSettingsUpdate(settings: UserSettings) {

    val previous = snapshot.value
    snapshot.update { it.withSettings(settings) }

    if (previous.hasTemperatureChange(settings)) {
      reformatTemperatures(previous.weatherData, settings.temperatureUnit)
    }

    if (previous.hasAiSettingsChange(settings)) {
      invalidateAiContent()
    }
  }

  private fun reformatTemperatures(weatherData: WeatherData?, unit: TemperatureUnit) {
    weatherData ?: return
    _state.update { stateFactory.reformatTemperatures(it, weatherData, unit) }
  }

  private fun invalidateAiContent() {
    _state.update {
      stateFactory.applyAiContent(
        briefing = BriefingUiState.Loading,
        current = it,
        playlist = PlaylistUiState.Loading
      )
    }
  }

  private fun onResumeLifecycle() {
    val weatherData = snapshot.value.weatherData ?: return
    refreshAiContent(weatherData)
  }

  private fun onReceiveLocationResult(action: ReceiveLocationResult) {
    loadWeather(
      cityName = action.cityName,
      latitude = action.latitude,
      longitude = action.longitude
    )
  }

  private fun onRefreshClick() {
    loadWeather()
  }

  private fun loadWeather(
    cityName: String = DEFAULT_CITY,
    latitude: Double = DEFAULT_LATITUDE,
    longitude: Double = DEFAULT_LONGITUDE
  ) {
    _state.update { Loading }
    getWeather(cityName, latitude, longitude)
      .onEach { result ->
        result.fold(
          onSuccess = ::onWeatherSuccess,
          onFailure = ::onWeatherError
        )
      }
      .launchIn(viewModelScope)
  }

  private fun onWeatherSuccess(data: WeatherData) {
    snapshot.update { it.copy(weatherData = data) }
    _state.update { stateFactory.create(data, snapshot.value.temperatureUnit) }
    refreshAiContent(data)
  }

  private fun refreshAiContent(data: WeatherData) {
    generateWeatherAiContent(weatherData = data)
      .onEach { result ->
        result.fold(
          onSuccess = ::onAiContentSuccess,
          onFailure = { onAiContentError() }
        )
      }
      .launchIn(viewModelScope)
  }

  private fun onWeatherError(error: Throwable) {
    _state.update { Error(error.message ?: resources.defaultError()) }
  }

  private fun onAiContentSuccess(content: WeatherAiContent) {
    _state.update {
      stateFactory.applyAiContent(
        briefing = BriefingUiState.Loaded(content.briefing),
        current = it,
        playlist = stateFactory.createPlaylist(content.playlist)
      )
    }
  }

  private fun onAiContentError() {
    _state.update {
      stateFactory.applyAiContent(
        briefing = BriefingUiState.Error,
        current = it,
        playlist = PlaylistUiState.Error
      )
    }
  }

  private companion object {
    const val DEFAULT_CITY = "Toruń"
    const val DEFAULT_LATITUDE = 53.0138
    const val DEFAULT_LONGITUDE = 18.5984
  }
}
