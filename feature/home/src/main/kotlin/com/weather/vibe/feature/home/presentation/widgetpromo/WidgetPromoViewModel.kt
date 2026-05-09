package com.weather.vibe.feature.home.presentation.widgetpromo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.WidgetPromoOutcome.Reveal
import com.weather.vibe.domain.settings.usecase.MarkWidgetPromoSeen
import com.weather.vibe.domain.settings.usecase.ResolveWidgetPromo
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoAction.AddClick
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoAction.DismissClick
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoAction.HomeReady
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoEvent.RequestPin
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoUiState.Hidden
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoUiState.Pending
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoUiState.Visible
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class WidgetPromoViewModel(
  private val markWidgetPromoSeen: MarkWidgetPromoSeen,
  private val resolveWidgetPromo: ResolveWidgetPromo
) : ViewModel() {

  private val _state = MutableStateFlow<WidgetPromoUiState>(Pending)
  val state: StateFlow<WidgetPromoUiState> = _state.asStateFlow()

  private val _event = Channel<WidgetPromoEvent>(capacity = BUFFERED)
  val event: Flow<WidgetPromoEvent> = _event.receiveAsFlow()

  private val errorHandler = CoroutineExceptionHandler { _, _ -> hide() }

  fun dispatch(action: WidgetPromoAction) {
    when (action) {
      is HomeReady -> onHomeReady(action)
      is AddClick -> onAddClick()
      is DismissClick -> onDismissClick()
    }
  }

  private fun onHomeReady(action: HomeReady) {

    if (_state.value !is Pending) return

    if (action.widgetAlreadyPinned) {
      acknowledgeAlreadyPinned()
      return
    }

    resolveWidget()
  }

  private fun acknowledgeAlreadyPinned() {
    hide()
    viewModelScope.launch(errorHandler) {
      markWidgetPromoSeen()
    }
  }

  private fun resolveWidget() {
    viewModelScope.launch(errorHandler) {
      val outcome = resolveWidgetPromo()
      _state.update { if (outcome == Reveal) Visible else Hidden }
    }
  }

  private fun onAddClick() {
    hide()
    viewModelScope.launch(errorHandler) {
      markWidgetPromoSeen()
      _event.send(RequestPin)
    }
  }

  private fun onDismissClick() {
    hide()
    viewModelScope.launch(errorHandler) {
      markWidgetPromoSeen()
    }
  }

  private fun hide() {
    _state.update { Hidden }
  }
}
