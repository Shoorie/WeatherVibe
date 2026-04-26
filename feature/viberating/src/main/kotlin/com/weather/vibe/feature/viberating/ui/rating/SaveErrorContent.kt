package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode.Companion.Assertive
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraftUiState
import com.weather.vibe.feature.viberating.preview.RatingFormDraftPreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.dismissError
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.errorNoteQuote
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.errorSummary
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.retry
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.saveErrorBody
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.saveErrorTitle
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.scaleLabel

@Composable
internal fun SaveErrorContent(
  draft: RatingFormDraftUiState,
  callbacks: RatingCardCallbacks
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) { liveRegion = Assertive }
  ) {
    ErrorTitle()
    Spacer(Modifier.height(ExtraSmall))
    ErrorBody()
    Spacer(Modifier.height(ExtraSmall))
    ErrorRatingSummary(draft = draft)
    if (draft.note.isNotBlank()) {
      Spacer(Modifier.height(ExtraSmall))
      ErrorNoteQuote(note = draft.note)
    }
    Spacer(Modifier.height(Medium))
    ErrorActionRow(
      onRetryClick = callbacks.onRetryClick,
      onDismissErrorClick = callbacks.onDismissErrorClick
    )
  }
}

@Composable
private fun ErrorTitle() {
  Text(
    text = saveErrorTitle(),
    style = typography.titleMedium,
    color = colors.error,
    fontWeight = FontWeight.SemiBold
  )
}

@Composable
private fun ErrorBody() {
  Text(
    text = saveErrorBody(),
    style = typography.bodySmall,
    color = colors.onSurfaceVariant
  )
}

@Composable
private fun ErrorRatingSummary(draft: RatingFormDraftUiState) {
  Text(
    text = errorSummary(
      scaleLabel = scaleLabel(draft.sliderValue),
      rating = draft.sliderValue
    ),
    style = typography.bodyMedium,
    color = ratingColor(draft.sliderValue),
    fontWeight = FontWeight.SemiBold
  )
}

@Composable
private fun ErrorNoteQuote(note: String) {
  Text(
    text = errorNoteQuote(note = note),
    style = typography.bodySmall,
    color = colors.onSurfaceVariant
  )
}

@Composable
private fun ErrorActionRow(
  onRetryClick: () -> Unit,
  onDismissErrorClick: () -> Unit
) {
  Row(horizontalArrangement = Arrangement.spacedBy(Small)) {
    Button(
      onClick = onRetryClick,
      modifier = Modifier.weight(1f),
      shape = shapes.cardSmall,
      colors = ButtonDefaults.buttonColors(
        containerColor = colors.accent,
        contentColor = colors.onAccent
      )
    ) {
      Text(retry())
    }
    OutlinedButton(
      onClick = onDismissErrorClick,
      modifier = Modifier.weight(1f),
      shape = shapes.cardSmall
    ) {
      Text(dismissError())
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(RatingFormDraftPreview::class)
  draft: RatingFormDraftUiState
) {
  WeatherVibeTheme {
    SaveErrorContent(
      draft = draft,
      callbacks = RatingCardCallbacks.Noop
    )
  }
}
