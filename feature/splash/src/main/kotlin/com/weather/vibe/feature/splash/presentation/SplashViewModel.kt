package com.weather.vibe.feature.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.IsWelcomeOnboardingSeen
import com.weather.vibe.feature.splash.presentation.SplashEvent.NavigateToHome
import com.weather.vibe.feature.splash.presentation.SplashEvent.NavigateToLocationOnboarding
import com.weather.vibe.feature.splash.presentation.SplashEvent.NavigateToWelcomeOnboarding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class SplashViewModel(
  private val isWelcomeOnboardingSeen: IsWelcomeOnboardingSeen,
  private val observeCurrentLocation: ObserveCurrentLocation
) : ViewModel() {

  private val _event = Channel<SplashEvent>(capacity = BUFFERED)
  val event: Flow<SplashEvent> = _event.receiveAsFlow()

  init {
    handleNavigation()
  }

  private fun handleNavigation() {
    combine(observeCurrentLocation(), isWelcomeOnboardingSeen()) { location, seen ->
      resolveDestination(location = location, welcomeSeen = seen)
    }
      .onEach(::send)
      .launchIn(viewModelScope)
  }

  private fun resolveDestination(location: Location?, welcomeSeen: Boolean): SplashEvent =
    when {
      location != null -> NavigateToHome(location)
      welcomeSeen -> NavigateToLocationOnboarding
      else -> NavigateToWelcomeOnboarding
    }

  private fun send(event: SplashEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
