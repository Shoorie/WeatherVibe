package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.NotRated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Rated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts

@Composable
internal fun RatingCard(
  modifier: Modifier = Modifier,
  state: RatingCardUiState,
  onSliderValueChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit,
  onRetryClicked: () -> Unit,
  onDismissErrorClicked: () -> Unit,
  onEditClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = Texts.sectionLabel().uppercase(),
      style = typography.labelMedium,
      color = colors.onSurfaceVariant,
      modifier = Modifier
        .padding(bottom = Padding.Small)
        .semantics { heading() }
    )
    VibeCard(contentPadding = Padding.Medium) {
      Column(modifier = Modifier.fillMaxWidth()) {
        RatingCardStateContent(
          state = state,
          onSliderValueChanged = onSliderValueChanged,
          onSaveClicked = onSaveClicked,
          onRetryClicked = onRetryClicked,
          onDismissErrorClicked = onDismissErrorClicked,
          onEditClicked = onEditClicked,
          onViewHistoryClicked = onViewHistoryClicked
        )
      }
    }
  }
}

@Composable
private fun RatingCardStateContent(
  state: RatingCardUiState,
  onSliderValueChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit,
  onRetryClicked: () -> Unit,
  onDismissErrorClicked: () -> Unit,
  onEditClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  when (state) {
    Loading -> LoadingContent()
    is NotRated -> DraftContent(
      draft = state.sliderDraft,
      touched = state.sliderTouched,
      saving = false,
      onSliderValueChanged = onSliderValueChanged,
      onSaveClicked = onSaveClicked,
      onViewHistoryClicked = onViewHistoryClicked
    )
    is Saving -> DraftContent(
      draft = state.sliderDraft,
      touched = true,
      saving = true,
      onSliderValueChanged = {},
      onSaveClicked = {},
      onViewHistoryClicked = onViewHistoryClicked
    )
    is SaveError -> SaveErrorContent(
      draft = state.sliderDraft,
      onRetryClicked = onRetryClicked,
      onDismissErrorClicked = onDismissErrorClicked
    )
    is Rated -> RatedContent(
      rating = state.rating,
      onEditClicked = onEditClicked,
      onViewHistoryClicked = onViewHistoryClicked
    )
  }
}

@Composable
private fun LoadingContent() {
  Text(
    text = Texts.cardTitle(),
    style = typography.titleMedium,
    color = colors.onSurface
  )
}

@PreviewLightDark
@Composable
private fun RatingCardPreview(
  @PreviewParameter(RatingCardStatePreview::class)
  state: RatingCardUiState
) {
  WeatherVibeTheme {
    RatingCard(
      modifier = Modifier
        .background(colors.backgroundGradientEnd)
        .padding(Padding.Medium),
      state = state,
      onSliderValueChanged = {},
      onSaveClicked = {},
      onRetryClicked = {},
      onDismissErrorClicked = {},
      onEditClicked = {},
      onViewHistoryClicked = {}
    )
  }
}
