package com.weather.vibe.data.weather.local.mapper

import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.LANGUAGE_TAG
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.LOCATION_ID
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.cachedSuggestion
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.suggestion
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class WeatherSuggestionEntityMapperTest {

  private val mapper = WeatherSuggestionEntityMapper()

  @Test
  fun `when outfit present, then round-trip preserves outfit`() {

    val original = cachedSuggestion(suggestion = suggestion(outfitSuggestion = "Raincoat, boots"))

    val roundTripped = mapper.toDomain(
      mapper.toEntity(
        cached = original,
        dispositionEntries = emptyList(),
        languageTag = LANGUAGE_TAG,
        locationId = LOCATION_ID
      )
    )

    expectThat(roundTripped.suggestion.outfitSuggestion).isEqualTo("Raincoat, boots")
  }

  @Test
  fun `when outfit absent, then round-trip keeps it null`() {

    val original = cachedSuggestion(suggestion = suggestion(outfitSuggestion = null))

    val roundTripped = mapper.toDomain(
      mapper.toEntity(
        cached = original,
        dispositionEntries = emptyList(),
        languageTag = LANGUAGE_TAG,
        locationId = LOCATION_ID
      )
    )

    expectThat(roundTripped.suggestion.outfitSuggestion).isNull()
  }

  @Test
  fun `when outfit null in domain, then entity stores empty string`() {

    val original = cachedSuggestion(suggestion = suggestion(outfitSuggestion = null))

    val entity = mapper.toEntity(
      cached = original,
      dispositionEntries = emptyList(),
      languageTag = LANGUAGE_TAG,
      locationId = LOCATION_ID
    )

    expectThat(entity.outfitSuggestion).isEqualTo("")
  }
}
