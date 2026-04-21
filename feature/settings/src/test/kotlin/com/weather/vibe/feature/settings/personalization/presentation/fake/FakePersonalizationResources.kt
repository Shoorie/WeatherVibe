package com.weather.vibe.feature.settings.personalization.presentation.fake

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures
import com.weather.vibe.feature.settings.personalization.presentation.fixture.PersonalizationFixtures.DEFAULT_ERROR
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources
import io.mockk.every
import io.mockk.mockk

internal fun fakePersonalizationResources(): PersonalizationResources =
  mockk<PersonalizationResources>(relaxed = false).apply {
    every { defaultError() } returns DEFAULT_ERROR
    every { briefToneLabel(any()) } answers {
      PersonalizationFixtures.toneLabel(firstArg<BriefTone>())
    }
    every { briefToneDescription(any()) } answers {
      PersonalizationFixtures.toneDescription(firstArg<BriefTone>())
    }
  }
