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
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
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
  onRetryClicked: () -> Unit,
  onDismissErrorClicked: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) { liveRegion = LiveRegionMode.Assertive }
  ) {
    Text(
      text = saveErrorTitle(),
      style = typography.titleMedium,
      color = colors.error,
      fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(Padding.ExtraSmall))
    Text(
      text = saveErrorBody(),
      style = typography.bodySmall,
      color = colors.onSurfaceVariant
    )
    Spacer(Modifier.height(Padding.ExtraSmall))
    Text(
      text = errorSummary(
        scaleLabel = scaleLabel(draft.sliderValue),
        rating = draft.sliderValue
      ),
      style = typography.bodyMedium,
      color = ratingColor(draft.sliderValue),
      fontWeight = FontWeight.SemiBold
    )
    if (draft.note.isNotBlank()) {
      Spacer(Modifier.height(Padding.ExtraSmall))
      Text(
        text = errorNoteQuote(note = draft.note),
        style = typography.bodySmall,
        color = colors.onSurfaceVariant
      )
    }
    Spacer(Modifier.height(Padding.Medium))
    ErrorActionRow(
      onRetryClicked = onRetryClicked,
      onDismissErrorClicked = onDismissErrorClicked
    )
  }
}

@Composable
private fun ErrorActionRow(
  onRetryClicked: () -> Unit,
  onDismissErrorClicked: () -> Unit
) {
  Row(horizontalArrangement = Arrangement.spacedBy(Padding.Small)) {
    Button(
      onClick = onRetryClicked,
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
      onClick = onDismissErrorClicked,
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
      onRetryClicked = {},
      onDismissErrorClicked = {}
    )
  }
}
