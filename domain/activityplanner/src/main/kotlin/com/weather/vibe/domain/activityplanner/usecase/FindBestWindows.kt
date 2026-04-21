package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ScoreTier.GOOD
import com.weather.vibe.domain.activityplanner.model.ScoredHour
import com.weather.vibe.domain.activityplanner.model.ScoredWindow
import org.koin.core.annotation.Factory

@Factory
class FindBestWindows {

  operator fun invoke(hours: List<ScoredHour>): List<ScoredWindow> =
    hours.filter(::qualifies)
      .groupIntoConsecutiveBlocks()
      .filter { it.size >= MIN_BLOCK_SIZE }
      .map(::toWindow)
      .sortedByDescending(ScoredWindow::averageScore)
      .take(MAX_WINDOWS)
      .sortedBy(ScoredWindow::start)

  private fun qualifies(hour: ScoredHour): Boolean =
    hour.score >= MIN_HOUR_SCORE

  private fun List<ScoredHour>.groupIntoConsecutiveBlocks(): List<List<ScoredHour>> =
    fold(mutableListOf<MutableList<ScoredHour>>()) { blocks, hour ->
      if (hour.extendsLastBlockIn(blocks)) blocks.last() += hour
      else blocks += mutableListOf(hour)
      blocks
    }

  private fun ScoredHour.extendsLastBlockIn(
    blocks: List<List<ScoredHour>>
  ): Boolean =
    blocks.lastOrNull()
      ?.last()
      ?.isFollowedBy(this) == true

  private fun ScoredHour.isFollowedBy(next: ScoredHour): Boolean =
    time.plusHours(1) == next.time

  private fun toWindow(block: List<ScoredHour>): ScoredWindow =
    ScoredWindow(
      start = block.first().time,
      end = block.last().time.plusHours(1),
      averageScore = block.map(ScoredHour::score).average().toInt(),
      averageTemperature = block.map(ScoredHour::temperature).average(),
      averageUvIndex = block.map(ScoredHour::uvIndex).average(),
      averageWindSpeed = block.map(ScoredHour::windSpeed).average(),
      maxPrecipitationProbability = block.maxOf(ScoredHour::precipitationProbability)
    )

  private companion object {

    val MIN_HOUR_SCORE = GOOD.minScore
    const val MIN_BLOCK_SIZE = 1
    const val MAX_WINDOWS = 3
  }
}
