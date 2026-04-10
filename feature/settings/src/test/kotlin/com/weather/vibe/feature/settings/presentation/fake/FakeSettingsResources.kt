package com.weather.vibe.feature.settings.presentation.fake

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.presentation.fixture.SettingsResourcesFixtures.DEFAULT_ERROR
import com.weather.vibe.feature.settings.presentation.fixture.SettingsResourcesFixtures.toneDescription
import com.weather.vibe.feature.settings.presentation.fixture.SettingsResourcesFixtures.toneLabel
import com.weather.vibe.feature.settings.ui.SettingsResources
import io.mockk.every
import io.mockk.mockk

internal fun fakeSettingsResources(): SettingsResources =
  mockk<SettingsResources>().apply {
    every { briefToneLabel(any()) } answers { toneLabel(firstArg<BriefTone>()) }
    every { briefToneDescription(any()) } answers { toneDescription(firstArg<BriefTone>()) }
    every { defaultError() } returns DEFAULT_ERROR
  }
