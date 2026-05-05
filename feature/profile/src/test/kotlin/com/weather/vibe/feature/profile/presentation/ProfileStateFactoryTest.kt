package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.viberating.model.VibeOverview
import com.weather.vibe.feature.profile.presentation.fake.fakeProfileResources
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.RETURNING_SUBTITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.STATUS_OFF
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.STATUS_ON
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.TONE_LABEL_WITTY
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_AVATAR
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_GREETING
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_SUBTITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.USERNAME_JOHN
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.greeting
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.profileSnapshot
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.profileSummary
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Empty
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isTrue

internal class ProfileStateFactoryTest {

  private val resources = fakeProfileResources()
  private val factory = ProfileStateFactory(resources = resources)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when initial state built, then greeting uses unnamed copy`() {

    val result = factory.initial()

    expectThat(result.header.greeting).isEqualTo(UNNAMED_GREETING)
  }

  @Test
  fun `when initial state built, then subtitle uses unnamed copy`() {

    val result = factory.initial()

    expectThat(result.header.subtitle).isEqualTo(UNNAMED_SUBTITLE)
  }

  @Test
  fun `when initial state built, then waving hand hidden`() {

    val result = factory.initial()

    expectThat(result.header.showWavingHand).isFalse()
  }

  @Test
  fun `when initial state built, then avatar uses unnamed placeholder`() {

    val result = factory.initial()

    expectThat(result.header.avatarInitial).isEqualTo(UNNAMED_AVATAR)
  }

  @Test
  fun `when initial state built, then brief tone label is empty`() {

    val result = factory.initial()

    expectThat(result.header.briefToneLabel).isEqualTo("")
  }

  @Test
  fun `when initial state built, then edit sheet hidden`() {

    val result = factory.initial()

    expectThat(result.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when initial state built, then quick stats listed in locations, brief, alerts order`() {

    val result = factory.initial()

    expectThat(result.quickStats.map { it.type })
      .isEqualTo(listOf(LOCATIONS, MORNING_BRIEF, ALERTS))
  }

  @Test
  fun `when initial state built, then morning brief stat is off`() {

    val brief = factory.initial().quickStats.first { it.type == MORNING_BRIEF }

    expectThat(brief.value).isEqualTo(STATUS_OFF)
  }

  @Test
  fun `when initial state built, then alerts stat is off`() {

    val alerts = factory.initial().quickStats.first { it.type == ALERTS }

    expectThat(alerts.value).isEqualTo(STATUS_OFF)
  }

  @Test
  fun `when initial state built, then vibe row is empty`() {

    val result = factory.initial()

    expectThat(result.vibeRow).isA<Empty>()
  }

  @Test
  fun `when initial state built, then appearance row is absent`() {

    val result = factory.initial()

    expectThat(result.appearanceRow).isNull()
  }

  @Test
  fun `when snapshot applied, then appearance options follow Light, Auto, Dark order`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(themeMode = AUTO)
    )

    expectThat(result.appearanceRow)
      .isNotNull()
      .get { options.map { it.mode } }
      .isEqualTo(listOf(LIGHT, AUTO, DARK))
  }

  @Test
  fun `given dark theme snapshot, when applied, then dark option is selected`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(themeMode = DARK)
    )

    expectThat(result.appearanceRow)
      .isNotNull()
      .get { options.first { it.mode == DARK }.isSelected }
      .isTrue()
  }

  @Test
  fun `given light theme snapshot, when applied, then light option is selected`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(themeMode = LIGHT)
    )

    expectThat(result.appearanceRow)
      .isNotNull()
      .get { options.first { it.mode == LIGHT }.isSelected }
      .isTrue()
  }

  @Test
  fun `given populated profile snapshot, when applied, then greeting uses user name`() {

    val result = factory.create(state = factory.initial(), snapshot = profileSnapshot())

    expectThat(result.header.greeting).isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `given populated profile snapshot, when applied, then subtitle switches to returning copy`() {

    val result = factory.create(state = factory.initial(), snapshot = profileSnapshot())

    expectThat(result.header.subtitle).isEqualTo(RETURNING_SUBTITLE)
  }

  @Test
  fun `given populated profile snapshot, when applied, then waving hand is shown`() {

    val result = factory.create(state = factory.initial(), snapshot = profileSnapshot())

    expectThat(result.header.showWavingHand).isTrue()
  }

  @Test
  fun `given lowercase username snapshot, when applied, then avatar uppercases first letter`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(profile = profileSummary(username = "john"))
    )

    expectThat(result.header.avatarInitial).isEqualTo("J")
  }

  @Test
  fun `given blank username snapshot, when applied, then avatar falls back to unnamed`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(profile = profileSummary(username = "  "))
    )

    expectThat(result.header.avatarInitial).isEqualTo(UNNAMED_AVATAR)
  }

  @Test
  fun `given blank username snapshot, when applied, then waving hand is hidden`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(profile = profileSummary(username = "  "))
    )

    expectThat(result.header.showWavingHand).isFalse()
  }

  @Test
  fun `given witty tone snapshot, when applied, then brief tone label uses witty mapping`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(settings = userSettings(briefTone = WITTY_AND_FRIENDLY))
    )

    expectThat(result.header.briefToneLabel).isEqualTo(TONE_LABEL_WITTY)
  }

  @Test
  fun `given new settings snapshot, when applied, then greeting is preserved`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(settings = userSettings(briefTone = FORMAL))
    )

    expectThat(result.header.greeting).isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `given morning brief enabled, when snapshot applied, then brief stat is on`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(settings = userSettings(morningBriefEnabled = true))
    )

    val brief = result.quickStats.first { it.type == MORNING_BRIEF }
    expectThat(brief.value).isEqualTo(STATUS_ON)
  }

  @Test
  fun `given alerts enabled, when snapshot applied, then alerts stat is on`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(settings = userSettings(weatherAlertsEnabled = true))
    )

    val alerts = result.quickStats.first { it.type == ALERTS }
    expectThat(alerts.value).isEqualTo(STATUS_ON)
  }

  @Test
  fun `given favorites count, when snapshot applied, then locations stat shows the count`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(favoritesCount = 5)
    )

    val locations = result.quickStats.first { it.type == LOCATIONS }
    expectThat(locations.value).isEqualTo("5")
  }

  @Test
  fun `given settings failure, when snapshot applied, then previous brief tone is preserved`() {

    val seeded = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(settings = userSettings(briefTone = WITTY_AND_FRIENDLY))
    )

    val result = factory.create(
      state = seeded,
      snapshot = profileSnapshot(settingsResult = Result.failure(RuntimeException("boom")))
    )

    expectThat(result.header.briefToneLabel).isEqualTo(TONE_LABEL_WITTY)
  }

  @Test
  fun `given favorites failure, when snapshot applied, then previous locations count is preserved`() {

    val seeded = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(favoritesCount = 7)
    )

    val result = factory.create(
      state = seeded,
      snapshot = profileSnapshot(favoritesCountResult = Result.failure(RuntimeException("boom")))
    )

    val locations = result.quickStats.first { it.type == LOCATIONS }
    expectThat(locations.value).isEqualTo("7")
  }

  @Test
  fun `given empty vibe overview, when snapshot applied, then vibe row stays empty`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(vibeOverview = VibeOverview.EMPTY)
    )

    expectThat(result.vibeRow).isA<Empty>()
  }

  @Test
  fun `given non-empty vibe overview, when snapshot applied, then vibe row becomes loaded`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(
        vibeOverview = VibeOverview(averageRating = 4.5, streakDays = 2, totalEntries = 4)
      )
    )

    expectThat(result.vibeRow).isA<Loaded>()
  }

  @Test
  fun `given non-empty vibe overview, when snapshot applied, then loaded shows formatted average`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(
        vibeOverview = VibeOverview(averageRating = 4.5, streakDays = 2, totalEntries = 4)
      )
    )

    expectThat(result.vibeRow)
      .isA<Loaded>()
      .get { averageLabel }.isEqualTo("4.5/5")
  }

  @Test
  fun `given streak above threshold, when snapshot applied, then streak label is shown`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(
        vibeOverview = VibeOverview(averageRating = 4.5, streakDays = 3, totalEntries = 5)
      )
    )

    expectThat(result.vibeRow)
      .isA<Loaded>()
      .get { streakLabel }.isEqualTo("3 days in a row")
  }

  @Test
  fun `given streak below threshold, when snapshot applied, then streak label is hidden`() {

    val result = factory.create(
      state = factory.initial(),
      snapshot = profileSnapshot(
        vibeOverview = VibeOverview(averageRating = 4.5, streakDays = 1, totalEntries = 3)
      )
    )

    expectThat(result.vibeRow)
      .isA<Loaded>()
      .get { streakLabel }.isNull()
  }

  @Test
  fun `given populated header, when edit sheet triggered, then sheet is seeded with header username`() {

    val seeded = factory.create(state = factory.initial(), snapshot = profileSnapshot())

    val result = factory.triggerEditSheet(state = seeded)

    expectThat(result.editSheet.username).isEqualTo(USERNAME_JOHN)
  }

  @Test
  fun `given populated header, when edit sheet triggered, then save is enabled`() {

    val seeded = factory.create(state = factory.initial(), snapshot = profileSnapshot())

    val result = factory.triggerEditSheet(state = seeded)

    expectThat(result.editSheet.canSave).isTrue()
  }

  @Test
  fun `given blank header, when edit sheet triggered, then save is disabled`() {

    val result = factory.triggerEditSheet(state = factory.initial())

    expectThat(result.editSheet.canSave).isFalse()
  }

  @Test
  fun `when edit sheet dismissed, then sheet is hidden`() {

    val triggered = factory.triggerEditSheet(state = factory.initial())

    val result = factory.dismissEditSheet(state = triggered)

    expectThat(result.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when username edited, then sheet username reflects input`() {

    val result = factory.editUsername(state = factory.initial(), value = USERNAME_JOHN)

    expectThat(result.editSheet.username).isEqualTo(USERNAME_JOHN)
  }

  @Test
  fun `given non-blank value, when username edited, then save is enabled`() {

    val result = factory.editUsername(state = factory.initial(), value = USERNAME_JOHN)

    expectThat(result.editSheet.canSave).isTrue()
  }

  @Test
  fun `given blank value, when username edited, then save is disabled`() {

    val result = factory.editUsername(state = factory.initial(), value = "   ")

    expectThat(result.editSheet.canSave).isFalse()
  }
}
