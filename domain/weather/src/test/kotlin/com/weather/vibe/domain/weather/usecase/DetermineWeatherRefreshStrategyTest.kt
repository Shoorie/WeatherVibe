package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.weather.model.SimplifiedCondition.RAINY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SUNNY
import com.weather.vibe.domain.weather.model.TemperatureRange.COLD
import com.weather.vibe.domain.weather.model.TemperatureRange.WARM
import com.weather.vibe.domain.weather.model.TimeOfDay.AFTERNOON
import com.weather.vibe.domain.weather.model.TimeOfDay.NIGHT
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.InvalidateAndRegenerate
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.ReformatOnly
import com.weather.vibe.domain.weather.model.WeatherRefreshStrategy.RegenerateSuggestion
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DetermineWeatherRefreshStrategyTest {

  private val determineStrategy = DetermineWeatherRefreshStrategy()

  @Test
  fun `given no previous key, when determined, then regenerate suggestion`() {

    val result = determineStrategy(
      previousWeatherKey = null,
      currentWeatherKey = SUNNY_WARM_AFTERNOON,
      previousSettings = null,
      currentSettings = userSettings()
    )

    expectThat(result).isEqualTo(RegenerateSuggestion)
  }

  @Test
  fun `given different weather key, when determined, then regenerate suggestion`() {

    val result = determineStrategy(
      previousWeatherKey = SUNNY_WARM_AFTERNOON,
      currentWeatherKey = RAINY_COLD_NIGHT,
      previousSettings = userSettings(),
      currentSettings = userSettings()
    )

    expectThat(result).isEqualTo(RegenerateSuggestion)
  }

  @Test
  fun `given same key and changed tone, when determined, then invalidate and regenerate`() {

    val result = determineStrategy(
      previousWeatherKey = SUNNY_WARM_AFTERNOON,
      currentWeatherKey = SUNNY_WARM_AFTERNOON,
      previousSettings = userSettings(briefTone = FORMAL),
      currentSettings = userSettings(briefTone = HUMOROUS)
    )

    expectThat(result).isEqualTo(InvalidateAndRegenerate)
  }

  @Test
  fun `given same key and only unit changed, when determined, then reformat only`() {

    val result = determineStrategy(
      previousWeatherKey = SUNNY_WARM_AFTERNOON,
      currentWeatherKey = SUNNY_WARM_AFTERNOON,
      previousSettings = userSettings(temperatureUnit = CELSIUS),
      currentSettings = userSettings(temperatureUnit = FAHRENHEIT)
    )

    expectThat(result).isEqualTo(ReformatOnly)
  }

  @Test
  fun `given same key and null previous settings, when determined, then reformat only`() {

    val result = determineStrategy(
      previousWeatherKey = SUNNY_WARM_AFTERNOON,
      currentWeatherKey = SUNNY_WARM_AFTERNOON,
      previousSettings = null,
      currentSettings = userSettings()
    )

    expectThat(result).isEqualTo(ReformatOnly)
  }

  private fun userSettings(
    briefTone: BriefTone = FORMAL,
    temperatureUnit: TemperatureUnit = CELSIUS
  ): UserSettings = UserSettings(
    briefTone = briefTone,
    excludedGenres = emptySet(),
    temperatureUnit = temperatureUnit
  )

  private companion object {

    val SUNNY_WARM_AFTERNOON = WeatherKey(
      condition = SUNNY,
      temperature = WARM,
      timeOfDay = AFTERNOON
    )

    val RAINY_COLD_NIGHT = WeatherKey(
      condition = RAINY,
      temperature = COLD,
      timeOfDay = NIGHT
    )
  }
}
