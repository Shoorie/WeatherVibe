package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.repository.RatingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
@Factory
class ObserveTodayEntries(
  private val repository: RatingRepository,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(): Flow<List<RatingEntry>> =
    currentDateUpdates()
      .flatMapLatest(repository::observeForDate)

  private fun currentDateUpdates(): Flow<LocalDate> = flow {
    var current = timeProvider.today()
    emit(current)
    while (true) {
      delay(MIDNIGHT_POLL_INTERVAL_MS)
      val today = timeProvider.today()
      if (today != current) {
        current = today
        emit(current)
      }
    }
  }.distinctUntilChanged()

  private companion object {
    const val MIDNIGHT_POLL_INTERVAL_MS: Long = 60_000L
  }
}
