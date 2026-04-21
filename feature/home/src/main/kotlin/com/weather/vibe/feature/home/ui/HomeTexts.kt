package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R

internal object HomeTexts {

  @Composable
  fun alertSectionLabel(): String =
    stringResource(R.string.home_alert_section_label)

  @Composable
  fun shareBriefContentDescription(): String =
    stringResource(R.string.share_brief_content_description)

  @Composable
  fun shareBriefActionLabel(): String =
    stringResource(R.string.share_brief_action_label)

  @Composable
  fun tryAgainContentDescription(): String =
    stringResource(R.string.try_again_content_description)
}
