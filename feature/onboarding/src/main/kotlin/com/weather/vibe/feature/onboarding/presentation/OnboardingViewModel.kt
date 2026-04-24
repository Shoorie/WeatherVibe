package com.weather.vibe.feature.onboarding.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.ObtainCurrentLocation
import com.weather.vibe.domain.location.usecase.PersistSelectedLocation
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.OpenSystemSettingsClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.PermissionResult
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.SearchCityClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.UseMyLocationClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.NavigateToHome
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.NavigateToSearch
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.OpenAppSettings
import com.weather.vibe.feature.onboarding.presentation.OnboardingEvent.RequestPermission
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.FETCHING_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.IDLE
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.PERMISSION_PERMANENTLY_DENIED
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.REQUESTING_PERMISSION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class OnboardingViewModel(
  private val obtainCurrentLocation: ObtainCurrentLocation,
  private val persistSelectedLocation: PersistSelectedLocation,
  private val stateFactory: OnboardingStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow(stateFactory.create(IDLE))
  val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

  private val _event = Channel<OnboardingEvent>(capacity = BUFFERED)
  val event: Flow<OnboardingEvent> = _event.receiveAsFlow()

  private val fallbackToSearchOnError = CoroutineExceptionHandler { _, throwable ->
    Log.w("OnboardingViewModel", "Failed to obtain location", throwable)
    handOverToSearch()
  }

  fun dispatch(action: OnboardingAction) {
    when (action) {
      is UseMyLocationClick -> onUseMyLocationClick()
      is SearchCityClick -> onSearchCityClick()
      is OpenSystemSettingsClick -> onOpenSystemSettingsClick()
      is PermissionResult -> onPermissionResult(action)
    }
  }

  private fun onUseMyLocationClick() {
    transitionTo(REQUESTING_PERMISSION)
    send(RequestPermission)
  }

  private fun onSearchCityClick() {
    handOverToSearch()
  }

  private fun onOpenSystemSettingsClick() {
    send(OpenAppSettings)
  }

  private fun onPermissionResult(action: PermissionResult) {
    when {
      action.granted -> fetchLocation()
      action.canAskAgain -> transitionTo(IDLE)
      else -> transitionTo(PERMISSION_PERMANENTLY_DENIED)
    }
  }

  private fun fetchLocation() {
    transitionTo(FETCHING_LOCATION)
    obtainCurrentLocation()
      .onEach(::onLocationResult)
      .launchIn(viewModelScope)
  }

  private fun onLocationResult(result: Result<Location>) {
    result
      .onSuccess(::onLocationResolved)
      .onFailure {
        Log.w("OnboardingViewModel", "Failed to obtain location", it)
        handOverToSearch()
      }
  }

  private fun onLocationResolved(location: Location) {
    viewModelScope.launch(fallbackToSearchOnError) {
      persistSelectedLocation(location)
      send(NavigateToHome(location))
    }
  }

  private fun handOverToSearch() {
    transitionTo(IDLE)
    send(NavigateToSearch)
  }

  private fun transitionTo(phase: OnboardingPhase) {
    _state.update { stateFactory.create(phase) }
  }

  private fun send(event: OnboardingEvent) {
    viewModelScope.launch {
      _event.send(event)
    }
  }
}
