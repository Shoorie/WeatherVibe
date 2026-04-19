package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

class BuildActivityPlanTest {

  private val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 9, 0))
  private val buildActivityPlan = BuildActivityPlan(
    approximateHourlyUvIndex = ApproximateHourlyUvIndex(),
    findBestWindows = FindBestWindows(),
    scoreHourForActivity = ScoreHourForActivity(
      calculateHourPenalty = CalculateHourPenalty(),
      collectScoreReasons = CollectScoreReasons()
    ),
    timeProvider = timeProvider
  )

  @Test
  fun `when plan built, then only today's future hours included`() {

    val weather = WeatherDataFixtures.weatherData()

    val plan = buildActivityPlan(weather, activity = ActivityType.RUNNING)

    expectThat(plan.scoredHours).hasSize(3)
  }

  @Test
  fun `given current time after all hours, then no hours included`() {

    timeProvider.current = LocalDateTime.of(2026, 4, 8, 20, 0)
    val weather = WeatherDataFixtures.weatherData()

    val plan = buildActivityPlan(weather, activity = ActivityType.RUNNING)

    expectThat(plan.scoredHours).hasSize(0)
  }

  @Test
  fun `when plan built, then plan carries selected activity`() {

    val weather = WeatherDataFixtures.weatherData()

    val plan = buildActivityPlan(weather, activity = ActivityType.CYCLING)

    expectThat(plan.activity).isEqualTo(ActivityType.CYCLING)
  }
}
