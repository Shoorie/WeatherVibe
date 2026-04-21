package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures
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
  fun `when plan built, then all forecast hours scored`() {

    val weather = WeatherDataFixtures.weatherData()

    val plan = buildActivityPlan(weather, activity = ActivityType.RUNNING)

    expectThat(plan.scoredHours).hasSize(3)
  }

  @Test
  fun `given empty forecast, then plan has no hours`() {

    val weather = WeatherDataFixtures.weatherData(hourlyForecast = emptyList())

    val plan = buildActivityPlan(weather, activity = ActivityType.RUNNING)

    expectThat(plan.scoredHours).isEmpty()
  }

  @Test
  fun `given forecast in foreign timezone, then window anchored to forecast's first hour`() {

    val foreignHours = (0..26).map { offset ->
      WeatherDataFixtures.hourlyWeather(
        time = LocalDateTime.of(2026, 4, 8, 23, 0).plusHours(offset.toLong())
      )
    }
    val weather = WeatherDataFixtures.weatherData(hourlyForecast = foreignHours)

    val plan = buildActivityPlan(weather, activity = ActivityType.RUNNING)

    expectThat(plan.scoredHours).hasSize(24)

    expectThat(plan.scoredHours.first().time)
      .isEqualTo(LocalDateTime.of(2026, 4, 8, 23, 0))

    expectThat(plan.scoredHours.last().time)
      .isEqualTo(LocalDateTime.of(2026, 4, 9, 22, 0))
  }

  @Test
  fun `when plan built, then plan carries selected activity`() {

    val weather = WeatherDataFixtures.weatherData()

    val plan = buildActivityPlan(weather, activity = ActivityType.CYCLING)

    expectThat(plan.activity).isEqualTo(ActivityType.CYCLING)
  }
}
