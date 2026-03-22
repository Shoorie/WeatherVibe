package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.feature.home.presentation.HomeAction.LocationResultReceived
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
  private val getWeather: GetWeather,
  private val stateFactory: HomeStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  init {
    onRefreshClick()
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is LocationResultReceived -> onLocationResultReceived(action)
      is RefreshClick -> onRefreshClick()
    }
  }

  private fun onLocationResultReceived(action: LocationResultReceived) {
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
        _state.update {
          result.fold(
            onSuccess = stateFactory::create,
            onFailure = { Error(it.message ?: DEFAULT_ERROR) }
          )
        }
      }
      .launchIn(viewModelScope)
  }

  private companion object {
    const val DEFAULT_CITY = "Toruń"
    const val DEFAULT_ERROR = "Unexpected error"
    const val DEFAULT_LATITUDE = 53.0138
    const val DEFAULT_LONGITUDE = 18.5984
  }
}
