package com.weather.vibe.feature.settings.personalization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BackClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BuyPremiumClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.GenreRemove
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.LockedPersonaClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.PaywallDismiss
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.PersonaSelect
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.ToneUnlockedViaAd
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.UpsellClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.NavigateBack
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.ShowPremiumUnavailable
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class PersonalizationViewModel(
  private val stateFactory: PersonalizationStateFactory,
  private val useCases: PersonalizationUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<PersonalizationUiState>(Loading)
  val state: StateFlow<PersonalizationUiState> = _state.asStateFlow()

  private val _event = Channel<PersonalizationEvent>()
  val event: Flow<PersonalizationEvent> = _event.receiveAsFlow()

  private val paywallTone = MutableStateFlow<BriefTone?>(null)

  private val availableTones: List<BriefTone> =
    useCases.getAvailableBriefTones()

  private val errorHandler = CoroutineExceptionHandler { _, _ -> showError() }

  init {
    combine(
      useCases.observeUserSettings(),
      useCases.observePremiumStatus(),
      useCases.observeLockedTones(),
      paywallTone
    ) { settings, premium, locked, paywall ->
      toUiState(settings, premium, locked, paywall)
    }
      .onEach { state -> _state.update { state } }
      .launchIn(viewModelScope)
  }

  private fun toUiState(
    settings: Result<UserSettings>,
    premium: Result<Boolean>,
    locked: Result<Set<BriefTone>>,
    paywall: BriefTone?
  ): PersonalizationUiState {
    val loadedSettings = settings.getOrNull() ?: return stateFactory.createError()
    val isPremium = premium.getOrNull() ?: return stateFactory.createError()
    val lockedTones = locked.getOrNull() ?: return stateFactory.createError()
    return stateFactory.create(
      availableTones = availableTones,
      isPremium = isPremium,
      lockedTones = lockedTones,
      paywallTone = paywall,
      settings = loadedSettings
    )
  }

  fun dispatch(action: PersonalizationAction) {
    when (action) {
      is BackClick -> onBackClick()
      is BuyPremiumClick -> onBuyPremiumClick()
      is GenreRemove -> onGenreRemove(action)
      is LockedPersonaClick -> onLockedPersonaClick(action)
      is PaywallDismiss -> onPaywallDismiss()
      is PersonaSelect -> onPersonaSelect(action)
      is TemperatureUnitToggle -> onTemperatureUnitToggle()
      is ToneUnlockedViaAd -> onToneUnlockedViaAd(action)
      is UpsellClick -> onUpsellClick()
    }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onPersonaSelect(action: PersonaSelect) {
    viewModelScope.launch(errorHandler) {
      useCases.selectBriefTone(action.tone)
    }
  }

  private fun onLockedPersonaClick(action: LockedPersonaClick) {
    paywallTone.update { action.tone }
  }

  private fun onUpsellClick() {
    send(ShowPremiumUnavailable)
  }

  private fun onPaywallDismiss() {
    paywallTone.update { null }
  }

  private fun onToneUnlockedViaAd(action: ToneUnlockedViaAd) {
    paywallTone.update { null }
    viewModelScope.launch(errorHandler) {
      useCases.unlockToneTemporarily(action.tone)
      useCases.selectBriefTone(action.tone)
    }
  }

  private fun onBuyPremiumClick() {
    paywallTone.update { null }
    send(ShowPremiumUnavailable)
  }

  private fun onGenreRemove(action: GenreRemove) {
    viewModelScope.launch(errorHandler) {
      useCases.includeGenre(action.genre)
    }
  }

  private fun onTemperatureUnitToggle() {
    viewModelScope.launch(errorHandler) {
      useCases.toggleTemperatureUnit()
    }
  }

  private fun showError() {
    _state.update { stateFactory.createError() }
  }

  private fun send(event: PersonalizationEvent) {
    viewModelScope.launch {
      _event.send(event)
    }
  }
}
