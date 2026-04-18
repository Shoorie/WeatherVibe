package com.weather.vibe.feature.home.presentation.fake

import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.airquality.model.PollenSpecies
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.ALERT_INDICATOR
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.AQI_ALERT_TITLE
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.POLLEN_ALERT_TITLE
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.POLLEN_INDICATOR
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.aqiAlertDescription
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.aqiAlertMessage
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.aqiChipDescription
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.aqiIndicator
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.aqiLabel
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.pollenAlertDescription
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.pollenAlertMessage
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.pollenChipDescription
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.pollenLabel
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.speciesJoin
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.speciesLabel
import com.weather.vibe.feature.home.ui.HomeAirQualityResources
import io.mockk.every
import io.mockk.mockk

internal fun fakeHomeAirQualityResources(): HomeAirQualityResources {

  val fake = mockk<HomeAirQualityResources>(relaxed = false)

  every { fake.airQualityIndicator(any()) } answers {
    aqiIndicator(firstArg<AqiLevel>())
  }
  every { fake.airQualityLabel(any()) } answers {
    aqiLabel(firstArg<AqiLevel>())
  }
  every { fake.airQualityChipContentDescription(any(), any()) } answers {
    aqiChipDescription(
      level = firstArg<AqiLevel>(),
      europeanAqi = secondArg<Int>()
    )
  }
  every { fake.pollenIndicator() } returns POLLEN_INDICATOR
  every { fake.pollenLabel(any()) } answers {
    pollenLabel(firstArg<PollenLevel>())
  }
  every { fake.pollenChipContentDescription(any(), any()) } answers {
    pollenChipDescription(
      level = firstArg<PollenLevel>(),
      species = secondArg<PollenSpecies>()
    )
  }
  every { fake.speciesLabel(any()) } answers {
    speciesLabel(firstArg<PollenSpecies>())
  }
  every { fake.joinSpecies(any()) } answers {
    speciesJoin(firstArg<List<PollenSpecies>>())
  }
  every { fake.alertIndicator() } returns ALERT_INDICATOR
  every { fake.aqiAlertTitle() } returns AQI_ALERT_TITLE
  every { fake.aqiAlertMessage(any()) } answers {
    aqiAlertMessage(firstArg<AqiLevel>())
  }
  every { fake.aqiAlertContentDescription(any(), any()) } answers {
    aqiAlertDescription(
      level = firstArg<AqiLevel>(),
      europeanAqi = secondArg<Int>()
    )
  }
  every { fake.pollenAlertTitle() } returns POLLEN_ALERT_TITLE
  every { fake.pollenAlertMessage(any()) } answers {
    pollenAlertMessage(firstArg<String>())
  }
  every { fake.pollenAlertContentDescription(any()) } answers {
    pollenAlertDescription(firstArg<String>())
  }

  return fake
}
