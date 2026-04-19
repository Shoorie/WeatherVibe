package com.weather.vibe.feature.activityplanner.presentation

import com.weather.vibe.domain.activityplanner.model.ActivityType.CYCLING
import com.weather.vibe.domain.activityplanner.model.ActivityType.RUNNING
import com.weather.vibe.domain.activityplanner.model.ScoreTier.EXCELLENT
import com.weather.vibe.domain.activityplanner.usecase.ClassifyScore
import com.weather.vibe.domain.activityplanner.usecase.ClassifyTemperatureComfort
import com.weather.vibe.domain.activityplanner.usecase.ClassifyUvCategory
import com.weather.vibe.domain.activityplanner.usecase.ClassifyWindCategory
import com.weather.vibe.domain.activityplanner.usecase.IsDateToday
import com.weather.vibe.feature.activityplanner.fixture.ActivityPlanFixtures.plan
import com.weather.vibe.feature.activityplanner.fixture.ActivityPlanFixtures.window
import com.weather.vibe.feature.activityplanner.presentation.fake.fakeActivityPlannerResources
import com.weather.vibe.feature.activityplanner.presentation.fixture.ActivityPlannerResourcesFixtures.emptyMessage
import com.weather.vibe.feature.activityplanner.presentation.fixture.ActivityPlannerResourcesFixtures.tierLabel
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loaded
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

class ActivityPlannerStateFactoryTest {

  private val isDateToday = mockk<IsDateToday>()
  private val factory = ActivityPlannerStateFactory(
    classifyScore = ClassifyScore(),
    classifyTemperatureComfort = ClassifyTemperatureComfort(),
    classifyUvCategory = ClassifyUvCategory(),
    classifyWindCategory = ClassifyWindCategory(),
    isDateToday = isDateToday,
    resources = fakeActivityPlannerResources()
  )

  @Before
  fun setUp() {
    every { isDateToday(any()) } returns true
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when plan created, then all activity segments produced`() {

    val state = factory.create(plan(activity = CYCLING))

    expectThat(state).isA<Loaded>()
      .get { activities }.hasSize(3)
  }

  @Test
  fun `given plan with windows, then empty message null`() {

    val plan = plan(windows = listOf(window()))
    val state = factory.create(plan) as Loaded

    expectThat(state.emptyMessage).isNull()
  }

  @Test
  fun `given plan without windows, then empty message provided`() {

    val plan = plan(windows = emptyList())
    val state = factory.create(plan) as Loaded

    expectThat(state.emptyMessage)
      .isNotNull()
      .isEqualTo(emptyMessage(RUNNING))
  }

  @Test
  fun `when window mapped, then score tier classified`() {

    val plan = plan(windows = listOf(window(averageScore = 90)))
    val state = factory.create(plan) as Loaded

    expectThat(state.topWindows.single().tier).isEqualTo(EXCELLENT)
  }

  @Test
  fun `when window mapped, then tier label resolved`() {

    val plan = plan(windows = listOf(window(averageScore = 90)))
    val state = factory.create(plan) as Loaded

    expectThat(state.topWindows.single().tierLabel)
      .isEqualTo(tierLabel(EXCELLENT))
  }

  @Test
  fun `given window entirely today, then time range has no day suffix`() {

    every { isDateToday(any()) } returns true

    val plan = plan(windows = listOf(window()))
    val state = factory.create(plan) as Loaded

    expectThat(state.topWindows.single().timeRange).isEqualTo("16:00 – 18:00")
  }

  @Test
  fun `given window crossing midnight, then day suffix attached only to end hour`() {

    every { isDateToday(LocalDate.of(2026, 4, 13)) } returns true
    every { isDateToday(LocalDate.of(2026, 4, 14)) } returns false
    val start = LocalDateTime.of(2026, 4, 13, 23, 0)
    val end = LocalDateTime.of(2026, 4, 14, 1, 0)

    val plan = plan(windows = listOf(window(start = start, end = end)))
    val state = factory.create(plan) as Loaded

    expectThat(state.topWindows.single().timeRange)
      .isEqualTo("23:00 – 01:00 (${end.format(DAY_ABBREVIATION)})")
  }

  @Test
  fun `given window entirely tomorrow, then both hours carry day suffix`() {

    every { isDateToday(any()) } returns false
    val start = LocalDateTime.of(2026, 4, 14, 8, 0)
    val end = LocalDateTime.of(2026, 4, 14, 10, 0)
    val day = start.format(DAY_ABBREVIATION)

    val plan = plan(windows = listOf(window(start = start, end = end)))
    val state = factory.create(plan) as Loaded

    expectThat(state.topWindows.single().timeRange)
      .isEqualTo("08:00 ($day) – 10:00 ($day)")
  }

  private companion object {
    val DAY_ABBREVIATION: DateTimeFormatter = ofPattern("EEE")
  }
}
