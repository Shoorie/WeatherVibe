package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.weather.model.WeatherAiContent
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GenerateWeatherAiContent
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.feature.home.presentation.HomeAction.ReceiveLocationResult
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
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
  private val getWeather: GetWeather,
  private val resources: HomeResources,
  private val stateFactory: HomeStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  init {
    onRefreshClick()
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is ReceiveLocationResult -> onReceiveLocationResult(action)
      is RefreshClick -> onRefreshClick()
    }
  }

  private fun onReceiveLocationResult(action: ReceiveLocationResult) {
    loadWeather(action.latitude, action.longitude, action.cityName)
  }

  private fun onRefreshClick() {
    loadWeather()
  }

  private fun loadWeather(
    latitude: Double = DEFAULT_LATITUDE,
    longitude: Double = DEFAULT_LONGITUDE,
    cityName: String = DEFAULT_CITY
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
    _state.update { stateFactory.create(data) }
    generateWeatherAiContent(data)
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
