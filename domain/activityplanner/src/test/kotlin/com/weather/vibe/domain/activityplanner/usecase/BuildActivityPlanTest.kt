package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ActivityType.RUNNING
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

class BuildActivityPlanTest {

  private val buildActivityPlan = BuildActivityPlan(
    approximateHourlyUvIndex = ApproximateHourlyUvIndex(),
    findBestWindows = FindBestWindows(),
    scoreHourForActivity = ScoreHourForActivity(
      calculateHourPenalty = CalculateHourPenalty(),
      collectScoreReasons = CollectScoreReasons()
    )
  )

  @Test
  fun `when plan built, then every forecast hour scored`() {

    val weather = weatherData()

    val plan = buildActivityPlan(weather, activity = RUNNING)

    expectThat(plan.scoredHours).hasSize(3)
  }

  @Test
  fun `given empty forecast, then plan has no scored hours`() {

    val weather = weatherData(hourlyForecast = emptyList())

    val plan = buildActivityPlan(weather, activity = RUNNING)

    expectThat(plan.scoredHours).isEmpty()
  }

  @Test
  fun `given forecast in foreign timezone, then first scored hour matches forecast start`() {

    val plan = buildActivityPlan(
      weather = weatherData(hourlyForecast = foreignTimezoneHours()),
      activity = RUNNING
    )

    expectThat(plan.scoredHours.first().time)
      .isEqualTo(FOREIGN_FIRST_HOUR)
  }

  @Test
  fun `given forecast in foreign timezone, then last scored hour ends 24 hour window`() {

    val plan = buildActivityPlan(
      weather = weatherData(hourlyForecast = foreignTimezoneHours()),
      activity = RUNNING
    )

    expectThat(plan.scoredHours.last().time)
      .isEqualTo(FOREIGN_FIRST_HOUR.plusHours(23))
  }

  @Test
  fun `when plan built, then plan activity matches selection`() {

    val weather = weatherData()

    val plan = buildActivityPlan(weather, activity = ActivityType.CYCLING)

    expectThat(plan.activity).isEqualTo(ActivityType.CYCLING)
  }

  private fun foreignTimezoneHours() =
    (0..26).map { offset ->
      hourlyWeather(time = FOREIGN_FIRST_HOUR.plusHours(offset.toLong()))
    }

  private companion object {
    val FOREIGN_FIRST_HOUR: LocalDateTime = LocalDateTime.of(2026, 4, 8, 23, 0)
  }
}
