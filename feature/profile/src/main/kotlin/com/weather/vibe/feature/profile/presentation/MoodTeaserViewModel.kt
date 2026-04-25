package com.weather.vibe.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.viberating.usecase.ComputeVibeStats
import com.weather.vibe.domain.viberating.usecase.ObserveRatingEntries
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MoodTeaserViewModel(
  observeRatingEntries: ObserveRatingEntries,
  computeVibeStats: ComputeVibeStats
) : ViewModel() {

  val state: StateFlow<MoodTeaserUiState> = observeRatingEntries()
    .map { entries ->
      val stats = computeVibeStats(entries)
      MoodTeaserUiState(
        hasData = entries.isNotEmpty(),
        averageRating = stats.averageRating,
        dayCount = stats.uniqueDayCount,
        favoriteCondition = stats.favoriteCondition
      )
    }
    .catch { emit(MoodTeaserUiState.EMPTY) }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT_MS),
      initialValue = MoodTeaserUiState.EMPTY
    )

  private companion object {
    const val SUBSCRIPTION_TIMEOUT_MS = 5_000L
  }
}
