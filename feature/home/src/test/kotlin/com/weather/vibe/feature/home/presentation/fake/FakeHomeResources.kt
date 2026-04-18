package com.weather.vibe.feature.home.presentation.fake

import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.CLOUD_COVER_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.DEFAULT_ERROR
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.DEW_POINT_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.DIRECTION_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.FINDING_BETTER_SUGGESTIONS
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.HUMIDITY_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.NOW_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.PRECIPITATION_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.PRESSURE_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.RAINFALL_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.TODAY_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.UV_INDEX_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.VISIBILITY_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.WIND_GUSTS_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.WIND_SPEED_LABEL
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.SHARE_CHOOSER_TITLE
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.SHARE_WORDMARK_HEADLINE
import com.weather.vibe.feature.home.presentation.fixture.HomeResourcesFixtures.WIND_SPEED_MAX_LABEL
import com.weather.vibe.feature.home.ui.HomeResources
import io.mockk.every
import io.mockk.mockk

internal fun fakeHomeResources(): HomeResources =
  mockk<HomeResources>(relaxed = false).apply {
    every { conditionLabel(any()) } answers {
      firstArg<WeatherCondition>().label
    }
    every { cloudCover() } returns CLOUD_COVER_LABEL
    every { dayLengthFormat(any(), any()) } answers {
      "${firstArg<Int>()}h ${secondArg<Int>()}min"
    }
    every { defaultError() } returns DEFAULT_ERROR
    every { dewPoint() } returns DEW_POINT_LABEL
    every { direction() } returns DIRECTION_LABEL
    every { humidity() } returns HUMIDITY_LABEL
    every { nowLabel() } returns NOW_LABEL
    every { precipitation() } returns PRECIPITATION_LABEL
    every { pressure() } returns PRESSURE_LABEL
    every { rainfall() } returns RAINFALL_LABEL
    every { todayLabel() } returns TODAY_LABEL
    every { uvIndex() } returns UV_INDEX_LABEL
    every { visibility() } returns VISIBILITY_LABEL
    every { windGusts() } returns WIND_GUSTS_LABEL
    every { windSpeed() } returns WIND_SPEED_LABEL
    every { windSpeedMax() } returns WIND_SPEED_MAX_LABEL
    every { findingBetterSuggestions() } returns FINDING_BETTER_SUGGESTIONS
    every { shareWordmarkHeadline() } returns SHARE_WORDMARK_HEADLINE
    every { shareChooserTitle() } returns SHARE_CHOOSER_TITLE
  }
