package com.weather.vibe.feature.settings.presentation

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.feature.settings.presentation.fake.fakeSettingsResources
import com.weather.vibe.feature.settings.presentation.fixture.SettingsResourcesFixtures.toneDescription
import com.weather.vibe.feature.settings.presentation.fixture.SettingsResourcesFixtures.toneLabel
import com.weather.vibe.feature.settings.presentation.state.BriefToneOptionUiState
import com.weather.vibe.testing.settings.fixture.GenreFixtures.JAZZ
import com.weather.vibe.testing.settings.fixture.GenreFixtures.METAL
import com.weather.vibe.testing.settings.fixture.GenreFixtures.POP
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import strikt.assertions.map

class SettingsStateFactoryTest {

  private val factory = SettingsStateFactory(resources = fakeSettingsResources())
  private val availableTones = listOf(WITTY_AND_FRIENDLY, FORMAL, HUMOROUS)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given witty tone selected, when state created, then witty option is selected`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    expectThat(selectedTones(result.briefToneOptions))
      .containsExactly(WITTY_AND_FRIENDLY)
  }

  @Test
  fun `given formal tone selected, when state created, then formal option is selected`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(briefTone = FORMAL)
    )

    expectThat(selectedTones(result.briefToneOptions))
      .containsExactly(FORMAL)
  }

  @Test
  fun `given humorous tone selected, when state created, then humorous option is selected`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(briefTone = HUMOROUS)
    )

    expectThat(selectedTones(result.briefToneOptions))
      .containsExactly(HUMOROUS)
  }

  @Test
  fun `when state created, then exactly one brief tone option is selected`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(briefTone = FORMAL)
    )

    expectThat(selectedTones(result.briefToneOptions)).hasSize(1)
  }

  @Test
  fun `when state created, then brief tone options preserve available tones order`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    expectThat(result.briefToneOptions).map { it.tone }
      .containsExactly(WITTY_AND_FRIENDLY, FORMAL, HUMOROUS)
  }

  @Test
  fun `when state created, then brief tone options use resource labels`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    expectThat(result.briefToneOptions).map { it.label }
      .containsExactly(
        toneLabel(WITTY_AND_FRIENDLY),
        toneLabel(FORMAL),
        toneLabel(HUMOROUS)
      )
  }

  @Test
  fun `when state created, then brief tone options use resource descriptions`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    expectThat(result.briefToneOptions).map { it.description }
      .containsExactly(
        toneDescription(WITTY_AND_FRIENDLY),
        toneDescription(FORMAL),
        toneDescription(HUMOROUS)
      )
  }

  @Test
  fun `given celsius, when state created, then is celsius is true`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(temperatureUnit = CELSIUS)
    )

    expectThat(result.isCelsius).isTrue()
  }

  @Test
  fun `given fahrenheit, when state created, then is celsius is false`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(temperatureUnit = FAHRENHEIT)
    )

    expectThat(result.isCelsius).isFalse()
  }

  @Test
  fun `given empty excluded genres, when state created, then has excluded genres is false`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(excludedGenres = emptySet())
    )

    expectThat(result.hasExcludedGenres).isFalse()
  }

  @Test
  fun `given non-empty excluded genres, when state created, then has excluded genres is true`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(excludedGenres = setOf(POP))
    )

    expectThat(result.hasExcludedGenres).isTrue()
  }

  @Test
  fun `given unsorted excluded genres, when state created, then chips sorted alphabetically`() {

    val result = factory.create(
      availableTones = availableTones,
      settings = userSettings(excludedGenres = setOf(POP, METAL, JAZZ))
    )

    expectThat(result.genreChips).map { it.name }
      .containsExactly(JAZZ, METAL, POP)
  }

  private fun selectedTones(options: List<BriefToneOptionUiState>) =
    options.filter { it.isSelected }.map { it.tone }
}
