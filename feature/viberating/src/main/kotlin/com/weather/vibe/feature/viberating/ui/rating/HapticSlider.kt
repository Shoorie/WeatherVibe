package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.TextHandleMove
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.preview.HapticSliderPreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.sliderDescription
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.scaleLabel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HapticSlider(
  draft: Int,
  enabled: Boolean,
  activeColor: Color,
  onValueChange: (Int) -> Unit
) {
  val haptics = LocalHapticFeedback.current
  val resolvedDescription = sliderDescription()
  val resolvedScaleLabel = scaleLabel(draft)
  val sliderColors = SliderDefaults.colors(
    thumbColor = activeColor,
    activeTrackColor = activeColor,
    inactiveTrackColor = colors.outline
  )
  Slider(
    value = draft.toFloat(),
    onValueChange = { newValue ->
      val rounded = newValue.roundToInt().coerceIn(MIN_RATING, MAX_RATING)
      if (rounded != draft) {
        haptics.performHapticFeedback(TextHandleMove)
        onValueChange(rounded)
      }
    },
    valueRange = MIN_RATING.toFloat()..MAX_RATING.toFloat(),
    enabled = enabled,
    colors = sliderColors,
    track = { sliderState ->
      SliderDefaults.Track(
        sliderState = sliderState,
        colors = sliderColors,
        drawStopIndicator = null
      )
    },
    modifier = Modifier.semantics {
      contentDescription = resolvedDescription
      stateDescription = resolvedScaleLabel
    }
  )
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HapticSliderPreview::class)
  draft: Int
) {
  WeatherVibeTheme {
    HapticSlider(
      draft = draft,
      enabled = true,
      activeColor = ratingColor(draft),
      onValueChange = {}
    )
  }
}
