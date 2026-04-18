package com.weather.vibe.data.weather.remote.mapper

import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.OUTFIT_SUGGESTION
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION_JSON_WITH_BLANK_OUTFIT
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION_JSON_WITH_OUTFIT
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION_JSON_WITH_WHITESPACE_OUTFIT
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class WeatherSuggestionDtoMapperTest {

  private val mapper = WeatherSuggestionDtoMapper()

  @Test
  fun `when response has outfit, then outfit mapped to domain`() {

    val suggestion = mapper.toDomain(response = SUGGESTION_JSON_WITH_OUTFIT)

    expectThat(suggestion.outfitSuggestion).isEqualTo(OUTFIT_SUGGESTION)
  }

  @Test
  fun `when response outfit blank, then outfit mapped to null`() {

    val suggestion = mapper.toDomain(response = SUGGESTION_JSON_WITH_BLANK_OUTFIT)

    expectThat(suggestion.outfitSuggestion).isNull()
  }

  @Test
  fun `when response outfit surrounded by whitespace, then outfit trimmed`() {

    val suggestion = mapper.toDomain(response = SUGGESTION_JSON_WITH_WHITESPACE_OUTFIT)

    expectThat(suggestion.outfitSuggestion).isEqualTo(OUTFIT_SUGGESTION)
  }
}
