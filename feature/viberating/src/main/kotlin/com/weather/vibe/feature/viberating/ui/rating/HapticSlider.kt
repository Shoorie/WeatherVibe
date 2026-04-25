package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.sliderDescription
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.scaleLabel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HapticSlider(
  draft: Int,
  enabled: Boolean,
  activeColor: Color,
  onValueChanged: (Int) -> Unit
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
        haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        onValueChanged(rounded)
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
