package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.profile.presentation.fake.fakeProfileResources
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.ALERTS_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.LOCATIONS_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.MORNING_BRIEF_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.QUOTE_WITTY
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.STATUS_OFF
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.STATUS_ON
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.TONE_LABEL_WITTY
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_AVATAR
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_GREETING
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_SUBTITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.USERNAME_JOHN
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.days
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.greeting
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.profileSummary
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

internal class ProfileStateFactoryTest {

  private val resources = fakeProfileResources()
  private val factory = ProfileStateFactory(resources = resources)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when initial state built, then greeting uses unnamed greeting`() {

    val result = factory.initial()

    expectThat(result.header.greeting).isEqualTo(UNNAMED_GREETING)
  }

  @Test
  fun `when initial state built, then subtitle uses unnamed subtitle`() {

    val result = factory.initial()

    expectThat(result.header.subtitle).isEqualTo(UNNAMED_SUBTITLE)
  }

  @Test
  fun `when initial state built, then avatar shows unnamed placeholder`() {

    val result = factory.initial()

    expectThat(result.header.avatarInitial).isEqualTo(UNNAMED_AVATAR)
  }

  @Test
  fun `when initial state built, then brief tone label is empty`() {

    val result = factory.initial()

    expectThat(result.header.briefToneLabel).isEqualTo("")
  }

  @Test
  fun `when initial state built, then quote is empty`() {

    val result = factory.initial()

    expectThat(result.header.quote).isEqualTo("")
  }

  @Test
  fun `when initial state built, then edit sheet is hidden`() {

    val result = factory.initial()

    expectThat(result.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when initial state built, then quick stats contain locations morning brief and alerts`() {

    val result = factory.initial()

    expectThat(result.quickStats.map { it.type })
      .isEqualTo(listOf(LOCATIONS, MORNING_BRIEF, ALERTS))
  }

  @Test
  fun `when initial state built, then locations stat shows initial count`() {

    val result = factory.initial()
    val locations = result.quickStats.first { it.type == LOCATIONS }

    expectThat(locations.value).isEqualTo("1")
  }

  @Test
  fun `when initial state built, then morning brief stat shows off`() {

    val result = factory.initial()
    val brief = result.quickStats.first { it.type == MORNING_BRIEF }

    expectThat(brief.value).isEqualTo(STATUS_OFF)
  }

  @Test
  fun `when initial state built, then alerts stat shows off`() {

    val result = factory.initial()
    val alerts = result.quickStats.first { it.type == ALERTS }

    expectThat(alerts.value).isEqualTo(STATUS_OFF)
  }

  @Test
  fun `when profile applied, then greeting uses personalized text`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 1)
    )

    expectThat(result.header.greeting)
      .isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `when profile applied, then avatar shows first letter`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(username = USERNAME_JOHN)
    )

    expectThat(result.header.avatarInitial).isEqualTo("J")
  }

  @Test
  fun `when profile applied with lowercase username, then avatar uppercases first letter`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(username = "john")
    )

    expectThat(result.header.avatarInitial).isEqualTo("J")
  }

  @Test
  fun `given blank username, when profile applied, then avatar falls back to unnamed`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(username = "  ", usageDays = 1)
    )

    expectThat(result.header.avatarInitial).isEqualTo(UNNAMED_AVATAR)
  }

  @Test
  fun `when profile applied, then subtitle uses usage days`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 42)
    )

    expectThat(result.header.subtitle).isEqualTo(days(42))
  }

  @Test
  fun `when settings applied with witty tone, then label uses witty mapping`() {

    val result = factory.withSettings(
      state = factory.initial(),
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    expectThat(result.header.briefToneLabel).isEqualTo(TONE_LABEL_WITTY)
  }

  @Test
  fun `when settings applied with witty tone, then quote uses witty mapping`() {

    val result = factory.withSettings(
      state = factory.initial(),
      settings = userSettings(briefTone = WITTY_AND_FRIENDLY)
    )

    expectThat(result.header.quote).isEqualTo(QUOTE_WITTY)
  }

  @Test
  fun `when settings applied, then greeting preserved`() {

    val seeded = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 1)
    )

    val result = factory.withSettings(
      state = seeded,
      settings = userSettings(briefTone = FORMAL)
    )

    expectThat(result.header.greeting)
      .isEqualTo(greeting(USERNAME_JOHN))
  }

  @Test
  fun `given morning brief enabled, when settings applied, then brief stat shows on`() {

    val result = factory.withSettings(
      state = factory.initial(),
      settings = userSettings(morningBriefEnabled = true)
    )
    val brief = result.quickStats.first { it.type == MORNING_BRIEF }

    expectThat(brief.value).isEqualTo(STATUS_ON)
  }

  @Test
  fun `given morning brief disabled, when settings applied, then brief stat shows off`() {

    val result = factory.withSettings(
      state = factory.initial(),
      settings = userSettings(morningBriefEnabled = false)
    )
    val brief = result.quickStats.first { it.type == MORNING_BRIEF }

    expectThat(brief.value).isEqualTo(STATUS_OFF)
  }

  @Test
  fun `given alerts enabled, when settings applied, then alerts stat shows on`() {

    val result = factory.withSettings(
      state = factory.initial(),
      settings = userSettings(alertsEnabled = true)
    )
    val alerts = result.quickStats.first { it.type == ALERTS }

    expectThat(alerts.value).isEqualTo(STATUS_ON)
  }

  @Test
  fun `given alerts disabled, when settings applied, then alerts stat shows off`() {

    val result = factory.withSettings(
      state = factory.initial(),
      settings = userSettings(alertsEnabled = false)
    )
    val alerts = result.quickStats.first { it.type == ALERTS }

    expectThat(alerts.value).isEqualTo(STATUS_OFF)
  }

  @Test
  fun `when profile applied, then locations stat label uses resource mapping`() {

    val result = factory.initial()
    val locations = result.quickStats.first { it.type == LOCATIONS }

    expectThat(locations.label).isEqualTo(LOCATIONS_LABEL)
  }

  @Test
  fun `when initial state built, then morning brief stat label uses resource mapping`() {

    val result = factory.initial()
    val brief = result.quickStats.first { it.type == MORNING_BRIEF }

    expectThat(brief.label).isEqualTo(MORNING_BRIEF_LABEL)
  }

  @Test
  fun `when initial state built, then alerts stat label uses resource mapping`() {

    val result = factory.initial()
    val alerts = result.quickStats.first { it.type == ALERTS }

    expectThat(alerts.label).isEqualTo(ALERTS_LABEL)
  }

  @Test
  fun `when edit sheet triggered, then sheet visible`() {

    val result = factory.triggerEditSheet(state = factory.initial())

    expectThat(result.editSheet.isVisible).isTrue()
  }

  @Test
  fun `when edit sheet triggered, then sheet username matches header`() {

    val named = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 1)
    )

    val result = factory.triggerEditSheet(state = named)

    expectThat(result.editSheet.username).isEqualTo(USERNAME_JOHN)
  }

  @Test
  fun `given header username populated, when edit sheet triggered, then can save true`() {

    val named = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 1)
    )

    val result = factory.triggerEditSheet(state = named)

    expectThat(result.editSheet.canSave).isTrue()
  }

  @Test
  fun `given header username blank, when edit sheet triggered, then can save false`() {

    val result = factory.triggerEditSheet(state = factory.initial())

    expectThat(result.editSheet.canSave).isFalse()
  }

  @Test
  fun `when edit sheet dismissed, then sheet hidden`() {

    val triggered = factory.triggerEditSheet(state = factory.initial())

    val result = factory.dismissEditSheet(state = triggered)

    expectThat(result.editSheet.isVisible).isFalse()
  }

  @Test
  fun `when edit username changed, then sheet username reflects input`() {

    val result = factory.editUsername(
      state = factory.initial(),
      value = USERNAME_JOHN
    )

    expectThat(result.editSheet.username).isEqualTo(USERNAME_JOHN)
  }

  @Test
  fun `when edit username populated, then can save true`() {

    val result = factory.editUsername(
      state = factory.initial(),
      value = USERNAME_JOHN
    )

    expectThat(result.editSheet.canSave).isTrue()
  }

  @Test
  fun `when edit username blank, then can save false`() {

    val result = factory.editUsername(state = factory.initial(), value = "   ")

    expectThat(result.editSheet.canSave).isFalse()
  }
}
