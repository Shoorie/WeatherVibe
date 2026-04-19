package com.weather.vibe.feature.activityplanner.ui.screen.callbacks

import androidx.compose.runtime.Stable
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerAction.ActivitySelect
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerAction.BackClick
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerAction.RetryClick
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerViewModel

@Stable
internal class ActivityPlannerCallbacks(viewModel: ActivityPlannerViewModel) {
  val onActivitySelect: (ActivityType) -> Unit = { viewModel.dispatch(ActivitySelect(it)) }
  val onBackClick: () -> Unit = { viewModel.dispatch(BackClick) }
  val onRetryClick: () -> Unit = { viewModel.dispatch(RetryClick) }
}
