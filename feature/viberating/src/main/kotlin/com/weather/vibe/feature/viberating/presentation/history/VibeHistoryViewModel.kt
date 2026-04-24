package com.weather.vibe.feature.viberating.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.usecase.ComputeVibeStats
import com.weather.vibe.domain.viberating.usecase.ObserveRatingEntries
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.BackClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DayDetailDismissed
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DaySelected
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.NextMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.PreviousMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryEvent.NavigateBack
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import kotlinx.coroutines.channels.Channel
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
import java.time.LocalDate
import java.time.YearMonth

@KoinViewModel
internal class VibeHistoryViewModel(
  private val observeRatingEntries: ObserveRatingEntries,
  private val computeVibeStats: ComputeVibeStats,
  private val timeProvider: TimeProvider,
  private val stateFactory: VibeHistoryStateFactory
) : ViewModel() {

  private val today: LocalDate = timeProvider.today()
  private val currentMonth: YearMonth = YearMonth.from(today)

  private val entriesByDate = MutableStateFlow<Map<LocalDate, RatingEntry>>(emptyMap())
  private val viewMonthFlow = MutableStateFlow(currentMonth)
  private val selectedDateFlow = MutableStateFlow<LocalDate?>(null)

  private val _state = MutableStateFlow(VibeHistoryUiState.EMPTY)
  val state: StateFlow<VibeHistoryUiState> = _state.asStateFlow()

  private val eventChannel = Channel<VibeHistoryEvent>(Channel.BUFFERED)
  val event: Flow<VibeHistoryEvent> = eventChannel.receiveAsFlow()

  init {
    observeEntries()
  }

  fun dispatch(action: VibeHistoryAction) {
    when (action) {
      PreviousMonthClick -> onPreviousMonthClick()
      NextMonthClick -> onNextMonthClick()
      is DaySelected -> onDaySelected(action.date)
      DayDetailDismissed -> onDayDetailDismissed()
      BackClick -> send(NavigateBack)
    }
  }

  private fun observeEntries() {
    observeRatingEntries()
      .onEach { entries -> onEntriesLoaded(entries) }
      .launchIn(viewModelScope)
  }

  private fun onEntriesLoaded(entries: List<RatingEntry>) {
    entriesByDate.value = entries.associateBy(RatingEntry::date)
    recomputeState(entries = entries)
  }

  private fun onPreviousMonthClick() {
    viewMonthFlow.update { it.minusMonths(1) }
    recomputeState()
  }

  private fun onNextMonthClick() {
    val next = viewMonthFlow.value.plusMonths(1)
    if (next > currentMonth) return
    viewMonthFlow.value = next
    recomputeState()
  }

  private fun onDaySelected(date: LocalDate) {
    if (date.isAfter(today)) return
    selectedDateFlow.value = if (selectedDateFlow.value == date) null else date
    recomputeState()
  }

  private fun onDayDetailDismissed() {
    selectedDateFlow.value = null
    recomputeState()
  }

  private fun recomputeState(entries: List<RatingEntry> = entriesByDate.value.values.toList()) {
    _state.value = stateFactory.create(
      entriesByDate = entriesByDate.value,
      stats = computeVibeStats(entries),
      viewMonth = viewMonthFlow.value,
      currentMonth = currentMonth,
      today = today,
      selectedDate = selectedDateFlow.value
    )
  }

  private fun send(event: VibeHistoryEvent) {
    viewModelScope.launch { eventChannel.send(event) }
  }
}
