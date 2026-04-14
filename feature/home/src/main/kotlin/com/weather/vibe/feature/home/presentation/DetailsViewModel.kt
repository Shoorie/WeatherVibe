package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.factory.HomeStateFactory
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
internal class DetailsViewModel(
  @InjectedParam private val selectedLocation: Location?,
  private val resources: HomeResources,
  private val stateFactory: HomeStateFactory,
  private val useCases: DetailsUseCases
) : ViewModel() {

  val state: StateFlow<HomeUiState> = combine(
    useCases.observeCachedWeather(selectedLocation.toResolvedCoordinates()),
    useCases.observeUserSettings(),
    ::toState
  ).stateIn(
    scope = viewModelScope,
    started = WhileSubscribed(SUBSCRIPTION_TIMEOUT_MS),
    initialValue = Loading
  )

  private fun toState(weather: WeatherData?, settingsResult: Result<UserSettings>): HomeUiState {
    if (weather == null) return Loading
    return settingsResult.fold(
      onSuccess = { settings -> onSettingsSuccess(weather, settings) },
      onFailure = ::onSettingsError
    )
  }

  private fun onSettingsSuccess(weather: WeatherData, settings: UserSettings): HomeUiState =
    stateFactory.create(data = weather, unit = settings.temperatureUnit)

  private fun onSettingsError(error: Throwable): HomeUiState =
    Error(error.message ?: resources.defaultError())

  private companion object {
    const val SUBSCRIPTION_TIMEOUT_MS = 5_000L
  }
}
