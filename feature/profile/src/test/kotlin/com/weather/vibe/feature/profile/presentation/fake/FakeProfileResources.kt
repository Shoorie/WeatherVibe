package com.weather.vibe.feature.profile.presentation.fake

import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.ALERTS_CLICK_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.ALERTS_EMOJI
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.ALERTS_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.APPEARANCE_BODY
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.APPEARANCE_TITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.LOCATIONS_CLICK_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.LOCATIONS_EMOJI
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.LOCATIONS_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.MORNING_BRIEF_CLICK_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.MORNING_BRIEF_EMOJI
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.MORNING_BRIEF_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.RETURNING_SUBTITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.STATUS_OFF
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.STATUS_ON
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_AVATAR
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_GREETING
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.UNNAMED_SUBTITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.VIBE_EMPTY_CLICK_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.VIBE_EMPTY_CTA
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.VIBE_LOADED_CLICK_LABEL
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.VIBE_TITLE
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.appearanceOptionLabel as fixtureAppearanceOptionLabel
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.greeting as fixtureGreeting
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.toneLabel as fixtureToneLabel
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.vibeAverageLabel as fixtureVibeAverageLabel
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures.vibeStreakLabel as fixtureVibeStreakLabel
import com.weather.vibe.feature.profile.ui.ProfileResources
import io.mockk.every
import io.mockk.mockk

internal fun fakeProfileResources(): ProfileResources =
  mockk<ProfileResources>(relaxed = false).apply {
    every { unnamedGreeting() } returns UNNAMED_GREETING
    every { unnamedSubtitle() } returns UNNAMED_SUBTITLE
    every { returningSubtitle() } returns RETURNING_SUBTITLE
    every { unnamedAvatar() } returns UNNAMED_AVATAR
    every { locationsStatLabel() } returns LOCATIONS_LABEL
    every { locationsStatEmoji() } returns LOCATIONS_EMOJI
    every { locationsStatClickLabel() } returns LOCATIONS_CLICK_LABEL
    every { morningBriefStatLabel() } returns MORNING_BRIEF_LABEL
    every { morningBriefStatEmoji() } returns MORNING_BRIEF_EMOJI
    every { morningBriefStatClickLabel() } returns MORNING_BRIEF_CLICK_LABEL
    every { alertsStatLabel() } returns ALERTS_LABEL
    every { alertsStatEmoji() } returns ALERTS_EMOJI
    every { alertsStatClickLabel() } returns ALERTS_CLICK_LABEL
    every { statStatus(enabled = true) } returns STATUS_ON
    every { statStatus(enabled = false) } returns STATUS_OFF
    every { greeting(any()) } answers { fixtureGreeting(firstArg()) }
    every { briefToneLabel(any()) } answers { fixtureToneLabel(firstArg<BriefTone>()) }
    every { vibeTitle() } returns VIBE_TITLE
    every { vibeAverageLabel(any<Double>()) } answers { fixtureVibeAverageLabel(firstArg()) }
    every { vibeStreakLabel(any()) } answers { fixtureVibeStreakLabel(firstArg()) }
    every { vibeEmptyCta() } returns VIBE_EMPTY_CTA
    every { vibeLoadedClickLabel() } returns VIBE_LOADED_CLICK_LABEL
    every { vibeEmptyClickLabel() } returns VIBE_EMPTY_CLICK_LABEL
    every { appearanceTitle() } returns APPEARANCE_TITLE
    every { appearanceBody() } returns APPEARANCE_BODY
    every { appearanceOptionLabel(any()) } answers { fixtureAppearanceOptionLabel(firstArg<ThemeMode>()) }
  }
