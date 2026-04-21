package com.weather.vibe.feature.profile.presentation

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.profile.presentation.fake.fakeProfileResources
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_GREETING
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_SUBTITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.QUOTE_FORMAL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.QUOTE_WITTY
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.TONE_LABEL_FORMAL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.TONE_LABEL_WITTY
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.USERNAME_JOHN
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.USERNAME_LONG
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.days
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.greeting
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.profileSummary
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
  fun `when initial state built, then cannot save with empty username`() {

    val result = factory.initial()

    expectThat(result.editSheet.canSave).isFalse()
  }

  @Test
  fun `when initial state built, then streak stat shows zero`() {

    val result = factory.initial()
    val streak = result.quickStats.first { it.id == "streak" }

    expectThat(streak.value).isEqualTo("0")
  }

  @Test
  fun `when initial state built, then locations stat matches initial count`() {

    val result = factory.initial()
    val locations = result.quickStats.first { it.id == "locations" }

    expectThat(locations.value).isEqualTo("1")
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
  fun `given blank username, when profile applied, then greeting falls back to unnamed`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(username = "", usageDays = 1)
    )

    expectThat(result.header.greeting).isEqualTo(UNNAMED_GREETING)
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
  fun `when profile applied, then brief tone label preserved`() {

    val seeded = factory.withBriefTone(
      state = factory.initial(),
      tone = FORMAL
    )

    val result = factory.withProfile(
      state = seeded,
      profile = profileSummary(usageDays = 1)
    )

    expectThat(result.header.briefToneLabel).isEqualTo(TONE_LABEL_FORMAL)
  }

  @Test
  fun `when profile applied, then quote preserved`() {

    val seeded = factory.withBriefTone(
      state = factory.initial(),
      tone = FORMAL
    )

    val result = factory.withProfile(
      state = seeded,
      profile = profileSummary(usageDays = 1)
    )

    expectThat(result.header.quote).isEqualTo(QUOTE_FORMAL)
  }

  @Test
  fun `when profile applied, then streak stat matches usage days`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 42)
    )
    val streak = result.quickStats.first { it.id == "streak" }

    expectThat(streak.value).isEqualTo("42")
  }

  @Test
  fun `when profile applied, then usage days field stored`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 7)
    )

    expectThat(result.usageDays).isEqualTo(7)
  }

  @Test
  fun `when profile applied, then locations stat preserved`() {

    val result = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 42)
    )
    val locations = result.quickStats.first { it.id == "locations" }

    expectThat(locations.value).isEqualTo("1")
  }

  @Test
  fun `when brief tone applied, then label uses resource mapping`() {

    val result = factory.withBriefTone(
      state = factory.initial(),
      tone = WITTY_AND_FRIENDLY
    )

    expectThat(result.header.briefToneLabel)
      .isEqualTo(TONE_LABEL_WITTY)
  }

  @Test
  fun `when brief tone applied, then quote uses resource mapping`() {

    val result = factory.withBriefTone(
      state = factory.initial(),
      tone = WITTY_AND_FRIENDLY
    )

    expectThat(result.header.quote).isEqualTo(QUOTE_WITTY)
  }

  @Test
  fun `when brief tone applied, then greeting preserved`() {

    val seeded = factory.withProfile(
      state = factory.initial(),
      profile = profileSummary(usageDays = 1)
    )

    val result = factory.withBriefTone(
      state = seeded,
      tone = WITTY_AND_FRIENDLY
    )

    expectThat(result.header.greeting)
      .isEqualTo(greeting(USERNAME_JOHN))
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

  @Test
  fun `when edit username overlong, then sheet preserves raw value`() {

    val result = factory.editUsername(
      state = factory.initial(),
      value = USERNAME_LONG
    )

    expectThat(result.editSheet.username).isEqualTo(USERNAME_LONG)
  }
}
