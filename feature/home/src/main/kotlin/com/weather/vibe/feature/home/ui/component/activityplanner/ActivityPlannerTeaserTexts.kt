package com.weather.vibe.feature.home.ui.component.activityplanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R

internal object ActivityPlannerTeaserTexts {

  @Composable
  fun title(): String =
    stringResource(R.string.home_activity_planner_title)

  @Composable
  fun subtitle(): String =
    stringResource(R.string.home_activity_planner_subtitle)

  @Composable
  fun contentDescription(): String =
    stringResource(R.string.home_activity_planner_content_description)
}
