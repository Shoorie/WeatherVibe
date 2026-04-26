package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.VibeOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveVibeOverview(
  private val computeStats: ComputeVibeStats,
  private val computeStreak: ComputeVibeStreak,
  private val observeEntries: ObserveRatingEntries
) {

  operator fun invoke(): Flow<VibeOverview> =
    observeEntries().map(::toOverview)

  private fun toOverview(entries: List<RatingEntry>): VibeOverview {
    val stats = computeStats(entries)
    return VibeOverview(
      averageRating = stats.averageRating,
      streakDays = computeStreak(entries),
      totalEntries = stats.totalEntries
    )
  }
}
