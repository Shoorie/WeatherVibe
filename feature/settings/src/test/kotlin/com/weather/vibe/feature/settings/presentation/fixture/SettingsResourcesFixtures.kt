package com.weather.vibe.feature.settings.presentation.fixture

import com.weather.vibe.domain.settings.model.BriefTone

internal object SettingsResourcesFixtures {

  const val DEFAULT_ERROR = "default-error"
  const val LABEL_PREFIX = "label-"
  const val DESCRIPTION_PREFIX = "desc-"

  fun toneLabel(tone: BriefTone): String =
    "$LABEL_PREFIX${tone.name}"

  fun toneDescription(tone: BriefTone): String =
    "$DESCRIPTION_PREFIX${tone.name}"
}
