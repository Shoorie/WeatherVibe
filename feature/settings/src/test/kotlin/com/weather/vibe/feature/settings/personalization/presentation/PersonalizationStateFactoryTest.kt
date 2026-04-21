package com.weather.vibe.feature.settings.personalization.presentation

import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.feature.settings.personalization.presentation.fake.fakePersonalizationResources
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.AVAILABLE_TONES
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.GENRE_JAZZ
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.GENRE_METAL
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.TONE_LABEL_WITTY
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

internal class PersonalizationStateFactoryTest {

  private val resources = fakePersonalizationResources()
  private val factory = PersonalizationStateFactory(resources = resources)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when initial state built, then brief tone options empty`() {

    val result = factory.initial()

    expectThat(result.briefToneOptions).isEmpty()
  }

  @Test
  fun `when initial state built, then genre chips empty`() {

    val result = factory.initial()

    expectThat(result.genreChips).isEmpty()
  }

  @Test
  fun `when initial state built, then has excluded genres false`() {

    val result = factory.initial()

    expectThat(result.hasExcludedGenres).isFalse()
  }

  @Test
  fun `when initial state built, then unit defaults to celsius`() {

    val result = factory.initial()

    expectThat(result.isCelsius).isTrue()
  }

  @Test
  fun `when state created, then selected tone flagged in options`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    val selected = result.briefToneOptions.single { it.isSelected }
    expectThat(selected.tone).isEqualTo(WITTY_AND_FRIENDLY)
  }

  @Test
  fun `when state created, then options reflect available tones`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings()
    )

    expectThat(result.briefToneOptions).hasSize(AVAILABLE_TONES.size)
  }

  @Test
  fun `when state created, then label uses resource mapping`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    val wittyOption = result.briefToneOptions.single { it.tone == WITTY_AND_FRIENDLY }
    expectThat(wittyOption.label).isEqualTo(TONE_LABEL_WITTY)
  }

  @Test
  fun `given celsius unit, when state created, then isCelsius true`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings(temperatureUnit = CELSIUS)
    )

    expectThat(result.isCelsius).isTrue()
  }

  @Test
  fun `given fahrenheit unit, when state created, then isCelsius false`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings(temperatureUnit = FAHRENHEIT)
    )

    expectThat(result.isCelsius).isFalse()
  }

  @Test
  fun `given excluded genres, when state created, then chips match sorted names`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings(
        excludedGenres = setOf(GENRE_METAL, GENRE_JAZZ)
      )
    )

    expectThat(result.genreChips.map { it.name })
      .containsExactly(GENRE_JAZZ, GENRE_METAL)
  }

  @Test
  fun `given excluded genres, when state created, then has excluded genres true`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings(excludedGenres = setOf(GENRE_JAZZ))
    )

    expectThat(result.hasExcludedGenres).isTrue()
  }

  @Test
  fun `given no excluded genres, when state created, then has excluded genres false`() {

    val result = factory.create(
      availableTones = AVAILABLE_TONES,
      settings = userSettings(excludedGenres = emptySet())
    )

    expectThat(result.hasExcludedGenres).isFalse()
  }

  @Test
  fun `when error created, then message matches default error resource`() {

    val result = factory.createError()

    expectThat(result).isA<PersonalizationUiState.Error>()
      .get { message }.isEqualTo(PersonalizationFixtures.DEFAULT_ERROR)
  }
}
