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
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState.Companion.emptyFor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
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

  private val viewMonthFlow = MutableStateFlow(currentMonth)
  private val selectedDateFlow = MutableStateFlow<LocalDate?>(null)

  private val entriesWithStatsFlow: Flow<Pair<List<RatingEntry>, VibeStats>> =
    observeRatingEntries()
      .distinctUntilChanged()
      .map { entries -> entries to computeVibeStats(entries) }
      .catch { emit(emptyList<RatingEntry>() to VibeStats.EMPTY) }

  val state: StateFlow<VibeHistoryUiState> =
    combine(
      entriesWithStatsFlow,
      viewMonthFlow,
      selectedDateFlow
    ) { entriesAndStats, month, selected ->
      val (entries, allTimeStats) = entriesAndStats
      val monthStats = computeVibeStats(entries.filter { YearMonth.from(it.date) == month })
      stateFactory.create(
        entriesByDate = entries.groupBy(RatingEntry::date),
        allTimeStats = allTimeStats,
        monthStats = monthStats,
        viewMonth = month,
        currentMonth = currentMonth,
        today = today,
        selectedDate = selected
      )
    }.stateIn(
      scope = viewModelScope,
      started = WhileSubscribed(SHARING_TIMEOUT_MS),
      initialValue = emptyFor(currentMonth)
    )

  private val _events = Channel<VibeHistoryEvent>(Channel.BUFFERED)
  val events: Flow<VibeHistoryEvent> = _events.receiveAsFlow()

  fun dispatch(action: VibeHistoryAction) {
    when (action) {
      is BackClick -> onBackClick()
      is DayDetailDismissed -> onDayDetailDismissed()
      is DaySelected -> onDaySelected(action.date)
      is NextMonthClick -> onNextMonthClick()
      is PreviousMonthClick -> onPreviousMonthClick()
    }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onPreviousMonthClick() {
    viewMonthFlow.update { it.minusMonths(1) }
  }

  private fun onNextMonthClick() {
    viewMonthFlow.update { month ->
      if (month < currentMonth) month.plusMonths(1) else month
    }
  }

  private fun onDaySelected(date: LocalDate) {

    if (date.isAfter(today)) return

    selectedDateFlow.update { current ->
      if (current == date) null else date
    }
  }

  private fun onDayDetailDismissed() {
    selectedDateFlow.value = null
  }

  private fun send(event: VibeHistoryEvent) {
    viewModelScope.launch {
      _events.send(event)
    }
  }

  companion object {
    private const val SHARING_TIMEOUT_MS: Long = 5_000
  }
}
