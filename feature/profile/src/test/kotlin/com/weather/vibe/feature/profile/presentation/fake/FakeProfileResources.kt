package com.weather.vibe.feature.profile.presentation.fake

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.profile.presentation.fixture.ProfileFixtures
import com.weather.vibe.feature.profile.ui.ProfileResources
import io.mockk.every
import io.mockk.mockk

internal fun fakeProfileResources(): ProfileResources =
  mockk<ProfileResources>(relaxed = false).apply {
    every { unnamedGreeting() } returns ProfileFixtures.UNNAMED_GREETING
    every { unnamedSubtitle() } returns ProfileFixtures.UNNAMED_SUBTITLE
    every { locationsStatLabel() } returns ProfileFixtures.LOCATIONS_LABEL
    every { streakStatLabel() } returns ProfileFixtures.STREAK_LABEL
    every { greeting(any()) } answers { ProfileFixtures.greeting(firstArg()) }
    every { daysWithAppSubtitle(any()) } answers { ProfileFixtures.days(firstArg()) }
    every { briefToneLabel(any()) } answers { ProfileFixtures.toneLabel(firstArg<BriefTone>()) }
    every { heroQuote(any()) } answers { ProfileFixtures.quote(firstArg<BriefTone>()) }
  }
