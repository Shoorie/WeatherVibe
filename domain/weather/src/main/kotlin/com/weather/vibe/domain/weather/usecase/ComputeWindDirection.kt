package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WindDirection
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
class ComputeWindDirection {

  operator fun invoke(degrees: Double): WindDirection {
    val sector = nearestSectorFor(degrees)
    return WindDirection.entries[sector]
  }

  private fun nearestSectorFor(degrees: Double): Int {
    val normalized = normalize(degrees)
    return (normalized / SECTOR_WIDTH)
      .roundToInt() % WindDirection.entries.size
  }

  private fun normalize(degrees: Double): Double {
    val mod = degrees % FULL_CIRCLE
    return if (mod < 0) mod + FULL_CIRCLE else mod
  }

  private companion object {
    const val FULL_CIRCLE = 360.0
    const val SECTOR_WIDTH = 45.0
  }
}
