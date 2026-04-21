package com.weather.vibe.domain.activityplanner.model

enum class ScoreTier(val minScore: Int) {
  EXCELLENT(minScore = 85),
  GOOD(minScore = 70),
  FAIR(minScore = 50),
  POOR(minScore = 0)
}
