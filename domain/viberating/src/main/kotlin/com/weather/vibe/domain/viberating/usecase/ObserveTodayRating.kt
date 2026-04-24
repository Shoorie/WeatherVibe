package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.repository.RatingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import java.time.LocalDate

@Factory
class ObserveTodayRating(
  private val repository: RatingRepository,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(): Flow<RatingEntry?> =
    todayFlow().flatMapLatest { today -> repository.observeForDate(today) }

  private fun todayFlow(): Flow<LocalDate> = flow {
    var current = timeProvider.today()
    emit(current)
    while (true) {
      delay(TICK_INTERVAL_MS)
      val now = timeProvider.today()
      if (now != current) {
        current = now
        emit(current)
      }
    }
  }.distinctUntilChanged()

  private companion object {
    const val TICK_INTERVAL_MS = 60_000L
  }
}
