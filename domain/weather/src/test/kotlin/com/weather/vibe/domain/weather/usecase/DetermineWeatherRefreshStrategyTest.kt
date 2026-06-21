package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.domain.weather.model.SimplifiedCondition.RAINY
import com.weather.vibe.domain.weather.model.TemperatureRange.COLD
import com.weather.vibe.domain.weather.model.TimeOfDay.NIGHT
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.ReformatOnly
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.RegenerateSuggestion
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.DEFAULT_SETTINGS
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER_KEY
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherKey
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DetermineWeatherRefreshStrategyTest {

  private val determineStrategy = DetermineWeatherRefreshStrategy()

  @Test
  fun `given no previous key, when determined, then regenerate suggestion`() {

    val result = determineStrategy(
      previousWeatherKey = null,
      currentWeatherKey = WEATHER_KEY,
      previousSettings = null,
      currentSettings = DEFAULT_SETTINGS
    )

    expectThat(result).isEqualTo(RegenerateSuggestion)
  }

  @Test
  fun `given different weather key, when determined, then regenerate suggestion`() {

    val rainyColdNight = weatherKey(condition = RAINY, temperature = COLD, timeOfDay = NIGHT)

    val result = determineStrategy(
      previousWeatherKey = WEATHER_KEY,
      currentWeatherKey = rainyColdNight,
      previousSettings = DEFAULT_SETTINGS,
      currentSettings = DEFAULT_SETTINGS
    )

    expectThat(result).isEqualTo(RegenerateSuggestion)
  }

  @Test
  fun `given same key and changed tone, when determined, then regenerate suggestion`() {

    val result = determineStrategy(
      previousWeatherKey = WEATHER_KEY,
      currentWeatherKey = WEATHER_KEY,
      previousSettings = userSettings(briefTone = FORMAL),
      currentSettings = userSettings(briefTone = HUMOROUS)
    )

    expectThat(result).isEqualTo(RegenerateSuggestion)
  }

  @Test
  fun `given same key and only unit changed, when determined, then reformat only`() {

    val result = determineStrategy(
      previousWeatherKey = WEATHER_KEY,
      currentWeatherKey = WEATHER_KEY,
      previousSettings = userSettings(temperatureUnit = CELSIUS),
      currentSettings = userSettings(temperatureUnit = FAHRENHEIT)
    )

    expectThat(result).isEqualTo(ReformatOnly)
  }

  @Test
  fun `given same key and null previous settings, when determined, then reformat only`() {

    val result = determineStrategy(
      previousWeatherKey = WEATHER_KEY,
      currentWeatherKey = WEATHER_KEY,
      previousSettings = null,
      currentSettings = DEFAULT_SETTINGS
    )

    expectThat(result).isEqualTo(ReformatOnly)
  }
}
