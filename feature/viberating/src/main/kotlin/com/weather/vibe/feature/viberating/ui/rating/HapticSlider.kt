package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts
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
  val stateLabel = VibeRatingResources.scaleLabel(draft)
  val description = Texts.sliderDescription()
  val sliderColors = SliderDefaults.colors(
    thumbColor = activeColor,
    activeTrackColor = activeColor,
    inactiveTrackColor = colors.outline
  )

  LaunchedEffect(draft) {
    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
  }

  Slider(
    value = draft.toFloat(),
    onValueChange = { newValue ->
      val rounded = newValue.roundToInt().coerceIn(RatingColors.MIN_RATING, RatingColors.MAX_RATING)
      if (rounded != draft) onValueChanged(rounded)
    },
    valueRange = RatingColors.MIN_RATING.toFloat()..RatingColors.MAX_RATING.toFloat(),
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
      contentDescription = description
      stateDescription = stateLabel
    }
  )
}
