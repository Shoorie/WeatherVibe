package com.weather.vibe.feature.activityplanner.presentation.fake

import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.domain.activityplanner.model.TemperatureComfort
import com.weather.vibe.domain.activityplanner.model.UvCategory
import com.weather.vibe.domain.activityplanner.model.WindCategory
import com.weather.vibe.feature.activityplanner.presentation.fixture.ActivityPlannerResourcesFixtures
import com.weather.vibe.feature.activityplanner.presentation.fixture.ActivityPlannerResourcesFixtures.DEFAULT_ERROR
import com.weather.vibe.feature.activityplanner.presentation.fixture.ActivityPlannerResourcesFixtures.TEMPERATURE_LABEL
import com.weather.vibe.feature.activityplanner.presentation.fixture.ActivityPlannerResourcesFixtures.UV_LABEL
import com.weather.vibe.feature.activityplanner.presentation.fixture.ActivityPlannerResourcesFixtures.WIND_LABEL
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources
import io.mockk.every
import io.mockk.mockk

internal fun fakeActivityPlannerResources(): ActivityPlannerResources =
  mockk<ActivityPlannerResources>().apply {
    every { activityLabel(any()) } answers {
      ActivityPlannerResourcesFixtures.activityLabel(firstArg<ActivityType>())
    }
    every { activityContentDescription(any()) } answers {
      ActivityPlannerResourcesFixtures.activityContentDescription(firstArg<ActivityType>())
    }
    every { emptyMessage(any()) } answers {
      ActivityPlannerResourcesFixtures.emptyMessage(firstArg<ActivityType>())
    }
    every { tierLabel(any()) } answers {
      ActivityPlannerResourcesFixtures.tierLabel(firstArg<ScoreTier>())
    }
    every { temperatureComfort(any()) } answers {
      ActivityPlannerResourcesFixtures.temperatureComfort(firstArg<TemperatureComfort>())
    }
    every { uvCategory(any()) } answers {
      ActivityPlannerResourcesFixtures.uvCategory(firstArg<UvCategory>())
    }
    every { windCategory(any()) } answers {
      ActivityPlannerResourcesFixtures.windCategory(firstArg<WindCategory>())
    }
    every { temperature(any()) } answers {
      ActivityPlannerResourcesFixtures.temperature(firstArg<Int>())
    }
    every { wind(any()) } answers {
      ActivityPlannerResourcesFixtures.wind(firstArg<Int>())
    }
    every { hour(any()) } answers {
      ActivityPlannerResourcesFixtures.hour(firstArg<Int>())
    }
    every { timeRange(any(), any()) } answers {
      ActivityPlannerResourcesFixtures.timeRange(firstArg<String>(), secondArg<String>())
    }
    every { hourWithDay(any(), any()) } answers {
      ActivityPlannerResourcesFixtures.hourWithDay(firstArg<String>(), secondArg<String>())
    }
    every { metricDescription(any(), any(), any()) } answers {
      ActivityPlannerResourcesFixtures.metricDescription(
        firstArg<String>(),
        secondArg<String>(),
        thirdArg<String>()
      )
    }
    every { windowDescription(any(), any(), any(), any(), any()) } answers {
      ActivityPlannerResourcesFixtures.windowDescription(
        firstArg<String>(),
        secondArg<String>(),
        thirdArg<String>(),
        arg<String>(3),
        arg<String>(4)
      )
    }
    every { timelineHourDescription(any(), any(), any()) } answers {
      ActivityPlannerResourcesFixtures.timelineHourDescription(
        firstArg<String>(),
        secondArg<String>(),
        thirdArg<Int>()
      )
    }
    every { temperatureLabel() } returns TEMPERATURE_LABEL
    every { uvLabel() } returns UV_LABEL
    every { windLabel() } returns WIND_LABEL
    every { defaultError() } returns DEFAULT_ERROR
  }
