package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SUNNY
import com.weather.vibe.domain.weather.model.TimeOfDay.AFTERNOON
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains

class BuildWeatherSuggestionPromptTest {

  private val build = BuildWeatherSuggestionPrompt()

  @Test
  fun `when prompt built, then outfit section instructs the model`() {

    val prompt = build(
      condition = SUNNY,
      excludedGenres = emptySet(),
      languageTag = LANGUAGE_TAG,
      temperatureCelsius = TEMPERATURE,
      timeOfDay = AFTERNOON,
      tone = FORMAL
    )

    expectThat(prompt).contains("OUTFIT SUGGESTION")
  }

  @Test
  fun `when prompt built, then output format declares outfit suggestion field`() {

    val prompt = build(
      condition = SUNNY,
      excludedGenres = emptySet(),
      languageTag = LANGUAGE_TAG,
      temperatureCelsius = TEMPERATURE,
      timeOfDay = AFTERNOON,
      tone = FORMAL
    )

    expectThat(prompt).contains("\"outfitSuggestion\"")
  }

  private companion object {
    const val LANGUAGE_TAG = "en"
    const val TEMPERATURE = 20.0
  }
}
