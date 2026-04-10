package com.weather.vibe.data.settings.persistence.mapper

import com.weather.vibe.data.settings.persistence.UserSettingsCacheData
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.testing.settings.fixture.GenreFixtures.JAZZ
import com.weather.vibe.testing.settings.fixture.GenreFixtures.METAL
import com.weather.vibe.testing.settings.fixture.GenreFixtures.POP
import com.weather.vibe.testing.settings.fixture.UserSettingsCacheDataFixtures.PREVIOUS_CITY
import com.weather.vibe.testing.settings.fixture.UserSettingsCacheDataFixtures.UNKNOWN_PERSONA
import com.weather.vibe.testing.settings.fixture.UserSettingsCacheDataFixtures.UNKNOWN_UNIT
import com.weather.vibe.testing.settings.fixture.UserSettingsCacheDataFixtures.userSettingsCacheData
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.DEFAULT_SETTINGS
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class SettingsCacheMapperTest {

  private val mapper = SettingsCacheMapper()

  @Test
  fun `given default cache data, when mapped to domain, then brief tone falls back to witty and friendly`() {

    val result = mapper.toDomain(UserSettingsCacheData.getDefaultInstance())

    expectThat(result.briefTone).isEqualTo(WITTY_AND_FRIENDLY)
  }

  @Test
  fun `given unknown persona, when mapped to domain, then brief tone falls back to witty and friendly`() {

    val cacheData = userSettingsCacheData(persona = UNKNOWN_PERSONA)

    val result = mapper.toDomain(cacheData)

    expectThat(result.briefTone).isEqualTo(WITTY_AND_FRIENDLY)
  }

  @Test
  fun `given default cache data, when mapped to domain, then temperature unit falls back to celsius`() {

    val result = mapper.toDomain(UserSettingsCacheData.getDefaultInstance())

    expectThat(result.temperatureUnit).isEqualTo(CELSIUS)
  }

  @Test
  fun `given unknown unit, when mapped to domain, then temperature unit falls back to celsius`() {

    val cacheData = userSettingsCacheData(temperatureUnit = UNKNOWN_UNIT)

    val result = mapper.toDomain(cacheData)

    expectThat(result.temperatureUnit).isEqualTo(CELSIUS)
  }

  @Test
  fun `given default cache data, when mapped to domain, then excluded genres is empty`() {

    val result = mapper.toDomain(UserSettingsCacheData.getDefaultInstance())

    expectThat(result.excludedGenres).isEmpty()
  }

  @Test
  fun `given known persona, when mapped to domain, then brief tone resolves the enum`() {

    val cacheData = userSettingsCacheData(persona = FORMAL.name)

    val result = mapper.toDomain(cacheData)

    expectThat(result.briefTone).isEqualTo(FORMAL)
  }

  @Test
  fun `given known unit, when mapped to domain, then temperature unit resolves the enum`() {

    val cacheData = userSettingsCacheData(temperatureUnit = FAHRENHEIT.name)

    val result = mapper.toDomain(cacheData)

    expectThat(result.temperatureUnit).isEqualTo(FAHRENHEIT)
  }

  @Test
  fun `given csv genres, when mapped to domain, then split into set`() {

    val cacheData = userSettingsCacheData(excludedGenres = "$POP,$METAL,$JAZZ")

    val result = mapper.toDomain(cacheData)

    expectThat(result.excludedGenres).containsExactlyInAnyOrder(JAZZ, METAL, POP)
  }

  @Test
  fun `given csv with whitespace, when mapped to domain, then trimmed into set`() {

    val cacheData = userSettingsCacheData(excludedGenres = " $POP , $METAL , $JAZZ ")

    val result = mapper.toDomain(cacheData)

    expectThat(result.excludedGenres).containsExactlyInAnyOrder(JAZZ, METAL, POP)
  }

  @Test
  fun `when mapped to cache, then persona uses enum name`() {

    val result = mapper.toCache(
      previous = UserSettingsCacheData.getDefaultInstance(),
      settings = userSettings(briefTone = HUMOROUS)
    )

    expectThat(result.aiPersona).isEqualTo(HUMOROUS.name)
  }

  @Test
  fun `when mapped to cache, then temperature unit uses enum name`() {

    val result = mapper.toCache(
      previous = UserSettingsCacheData.getDefaultInstance(),
      settings = userSettings(temperatureUnit = FAHRENHEIT)
    )

    expectThat(result.temperatureUnit).isEqualTo(FAHRENHEIT.name)
  }

  @Test
  fun `given empty excluded genres, when mapped to cache, then csv is empty string`() {

    val result = mapper.toCache(
      previous = UserSettingsCacheData.getDefaultInstance(),
      settings = DEFAULT_SETTINGS
    )

    expectThat(result.excludedGenres).isEqualTo("")
  }

  @Test
  fun `given multiple excluded genres, when mapped to cache, then csv sorted alphabetically`() {

    val result = mapper.toCache(
      previous = UserSettingsCacheData.getDefaultInstance(),
      settings = userSettings(excludedGenres = setOf(POP, METAL, JAZZ))
    )

    expectThat(result.excludedGenres).isEqualTo("$JAZZ,$METAL,$POP")
  }

  @Test
  fun `when mapped to cache, then preserves previous default city`() {

    val previous = userSettingsCacheData(defaultCity = PREVIOUS_CITY)

    val result = mapper.toCache(previous = previous, settings = DEFAULT_SETTINGS)

    expectThat(result.defaultCity).isEqualTo(PREVIOUS_CITY)
  }
}
