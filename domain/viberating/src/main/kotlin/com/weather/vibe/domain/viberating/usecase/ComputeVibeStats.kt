package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.viberating.model.ConditionAverage
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.VibeStats
import org.koin.core.annotation.Factory

@Factory
class ComputeVibeStats {

  operator fun invoke(entries: List<RatingEntry>): VibeStats {
    if (entries.isEmpty()) return VibeStats.EMPTY

    val conditionAverages = entries
      .groupBy { it.weather.condition }
      .map { (condition, bucket) ->
        ConditionAverage(
          condition = condition,
          averageRating = bucket.map(RatingEntry::rating).average(),
          entryCount = bucket.size
        )
      }
      .sortedByDescending(ConditionAverage::averageRating)

    return VibeStats(
      averageRating = entries.map(RatingEntry::rating).average(),
      totalEntries = entries.size,
      uniqueDayCount = entries.distinctBy(RatingEntry::date).size,
      favoriteCondition = conditionAverages.firstOrNull()?.condition,
      conditionAverages = conditionAverages
    )
  }
}
