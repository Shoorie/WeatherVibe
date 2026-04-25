package com.weather.vibe.feature.viberating.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.VibeStats
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

  private val viewMonthFlow = MutableStateFlow(currentMonth)
  private val selectedDateFlow = MutableStateFlow<LocalDate?>(null)

  private val entriesWithStatsFlow: Flow<Pair<List<RatingEntry>, VibeStats>> =
    observeRatingEntries()
      .distinctUntilChanged()
      .map { entries -> entries to computeVibeStats(entries) }
      .catch { emit(emptyList<RatingEntry>() to VibeStats.EMPTY) }

  val state: StateFlow<VibeHistoryUiState> =
    combine(entriesWithStatsFlow, viewMonthFlow, selectedDateFlow) { entriesAndStats, month, selected ->
      val (entries, stats) = entriesAndStats
      stateFactory.create(
        entriesByDate = entries.groupBy(RatingEntry::date),
        stats = stats,
        viewMonth = month,
        currentMonth = currentMonth,
        today = today,
        selectedDate = selected
      )
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(SHARING_TIMEOUT_MS),
      initialValue = VibeHistoryUiState.emptyFor(currentMonth)
    )

  private val eventChannel = Channel<VibeHistoryEvent>(Channel.BUFFERED)
  val event: Flow<VibeHistoryEvent> = eventChannel.receiveAsFlow()

  fun dispatch(action: VibeHistoryAction) {
    when (action) {
      PreviousMonthClick -> onPreviousMonthClick()
      NextMonthClick -> onNextMonthClick()
      is DaySelected -> onDaySelected(action.date)
      DayDetailDismissed -> onDayDetailDismissed()
      BackClick -> eventChannel.trySend(NavigateBack)
    }
  }

  private fun onPreviousMonthClick() {
    viewMonthFlow.update { it.minusMonths(1) }
  }

  private fun onNextMonthClick() {
    viewMonthFlow.update { month -> if (month < currentMonth) month.plusMonths(1) else month }
  }

  private fun onDaySelected(date: LocalDate) {
    if (date.isAfter(today)) return
    selectedDateFlow.update { current -> if (current == date) null else date }
  }

  private fun onDayDetailDismissed() {
    selectedDateFlow.value = null
  }

  companion object {
    private const val SHARING_TIMEOUT_MS: Long = 5_000
  }
}
