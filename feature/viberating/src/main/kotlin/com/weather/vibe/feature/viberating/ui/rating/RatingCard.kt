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
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Editing
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.preview.RatingCardPreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.sectionLabel

@Composable
internal fun RatingCard(
  modifier: Modifier = Modifier,
  state: RatingCardUiState,
  callbacks: RatingCardCallbacks
) {
  Column(modifier = modifier.fillMaxWidth()) {
    SectionHeading()
    VibeCard(contentPadding = Padding.Medium) {
      RatingCardStateContent(
        state = state,
        callbacks = callbacks
      )
    }
  }
}

@Composable
private fun SectionHeading() {
  Text(
    text = sectionLabel().uppercase(),
    style = typography.labelMedium,
    color = colors.onSurfaceVariant,
    modifier = Modifier
      .padding(bottom = Padding.Small)
      .semantics { heading() }
  )
}

@Composable
private fun RatingCardStateContent(
  state: RatingCardUiState,
  callbacks: RatingCardCallbacks
) {
  when (state) {
    Loading -> RatingLoadingContent()
    is Editing -> DraftContent(
      draft = state.draft,
      todayEntryCount = state.todayEntryCount,
      saving = false,
      callbacks = callbacks
    )
    is Saving -> DraftContent(
      draft = state.draft,
      todayEntryCount = state.todayEntryCount,
      saving = true,
      callbacks = callbacks
    )
    is SaveError -> SaveErrorContent(
      draft = state.draft,
      callbacks = callbacks
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(RatingCardPreview::class)
  state: RatingCardUiState
) {
  WeatherVibeTheme {
    RatingCard(
      modifier = Modifier
        .background(colors.backgroundGradientEnd)
        .padding(Padding.Medium),
      state = state,
      callbacks = RatingCardCallbacks.Noop
    )
  }
}
