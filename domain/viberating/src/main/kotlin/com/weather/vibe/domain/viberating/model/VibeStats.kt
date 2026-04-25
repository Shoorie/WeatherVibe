package com.weather.vibe.domain.viberating.model

data class VibeStats(
  val averageRating: Double,
  val totalEntries: Int,
  val uniqueDayCount: Int,
  val favoriteCondition: Condition?,
  val conditionAverages: List<ConditionAverage>
) {

  companion object {
    val EMPTY: VibeStats = VibeStats(
      averageRating = 0.0,
      totalEntries = 0,
      uniqueDayCount = 0,
      favoriteCondition = null,
      conditionAverages = emptyList()
    )
  }
}
