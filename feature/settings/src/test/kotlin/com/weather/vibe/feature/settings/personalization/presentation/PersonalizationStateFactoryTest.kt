package com.weather.vibe.feature.settings.personalization.presentation

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.personalization.presentation.fake.fakePersonalizationResources
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.AVAILABLE_TONES
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.GENRE_JAZZ
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.GENRE_METAL
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isTrue

internal class PersonalizationStateFactoryTest {

  private val resources = fakePersonalizationResources()
  private val factory = PersonalizationStateFactory(resources = resources)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when state created, then narrator reflects selected tone`() {

    val result = created(settings = userSettings(briefTone = WITTY_AND_FRIENDLY))

    expectThat(result.narrator.name)
      .isEqualTo(PersonalizationFixtures.toneLabel(WITTY_AND_FRIENDLY))
  }

  @Test
  fun `when state created, then personas reflect available tones`() {

    val result = created()

    expectThat(result.personas).hasSize(AVAILABLE_TONES.size)
  }

  @Test
  fun `when state created, then selected persona flagged`() {

    val result = created(settings = userSettings(briefTone = WITTY_AND_FRIENDLY))

    val selected = result.personas.single { it.isSelected }
    expectThat(selected.tone).isEqualTo(WITTY_AND_FRIENDLY)
  }

  @Test
  fun `given locked tone, when state created, then persona marked locked`() {

    val result = created(isPremium = false, lockedTones = setOf(COACH))

    val coach = result.personas.single { it.tone == COACH }
    expectThat(coach.isLocked).isTrue()
  }

  @Test
  fun `given premium user, when state created, then no persona locked`() {

    val result = created(isPremium = true, lockedTones = emptySet())

    expectThat(result.personas.none { it.isLocked }).isTrue()
  }

  @Test
  fun `when state created, then premium tone count matches premium tones`() {

    val result = created()

    expectThat(result.premiumToneCount).isEqualTo(AVAILABLE_TONES.count { it.isPremium })
  }

  @Test
  fun `given paywall tone, when state created, then paywall carries that tone`() {

    val result = created(paywallTone = COACH)

    expectThat(result.paywall).isNotNull().get { tone }.isEqualTo(COACH)
  }

  @Test
  fun `given no paywall tone, when state created, then paywall null`() {

    val result = created(paywallTone = null)

    expectThat(result.paywall).isNull()
  }

  @Test
  fun `given celsius unit, when state created, then isCelsius true`() {

    val result = created(settings = userSettings(temperatureUnit = CELSIUS))

    expectThat(result.isCelsius).isTrue()
  }

  @Test
  fun `given fahrenheit unit, when state created, then isCelsius false`() {

    val result = created(settings = userSettings(temperatureUnit = FAHRENHEIT))

    expectThat(result.isCelsius).isFalse()
  }

  @Test
  fun `given excluded genres, when state created, then chips match sorted names`() {

    val result = created(
      settings = userSettings(excludedGenres = setOf(GENRE_METAL, GENRE_JAZZ))
    )

    expectThat(result.genreChips.map { it.name })
      .containsExactly(GENRE_JAZZ, GENRE_METAL)
  }

  @Test
  fun `when error created, then message matches default error resource`() {

    val result = factory.createError()

    expectThat(result).isA<PersonalizationUiState.Error>()
      .get { message }.isEqualTo(PersonalizationFixtures.DEFAULT_ERROR)
  }

  private fun created(
    availableTones: List<BriefTone> = AVAILABLE_TONES,
    isPremium: Boolean = false,
    lockedTones: Set<BriefTone> = emptySet(),
    paywallTone: BriefTone? = null,
    settings: UserSettings = userSettings()
  ): Loaded =
    factory.create(
      availableTones = availableTones,
      isPremium = isPremium,
      lockedTones = lockedTones,
      paywallTone = paywallTone,
      settings = settings
    )
}
