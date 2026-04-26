package com.weather.vibe.domain.viberating.model

data class VibeOverview(
  val averageRating: Double,
  val streakDays: Int,
  val totalEntries: Int
) {

  val hasEntries: Boolean
    get() = totalEntries > NO_ENTRIES

  companion object {
    val EMPTY: VibeOverview = VibeOverview(
      averageRating = 0.0,
      streakDays = 0,
      totalEntries = 0
    )

    private const val NO_ENTRIES = 0
  }
}
