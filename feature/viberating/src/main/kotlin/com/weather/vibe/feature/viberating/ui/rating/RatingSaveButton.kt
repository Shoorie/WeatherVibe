package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.save
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.saving
import com.weather.vibe.feature.viberating.ui.rating.defaults.RatingCardDefaults.SaveSpinnerStroke
import com.weather.vibe.feature.viberating.ui.rating.defaults.RatingCardDefaults.SpinnerSize

@Composable
internal fun RatingSaveButton(
  modifier: Modifier = Modifier,
  saving: Boolean,
  enabled: Boolean,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    enabled = enabled,
    modifier = modifier.fillMaxWidth(),
    shape = shapes.cardSmall,
    colors = ButtonDefaults.buttonColors(
      containerColor = colors.accent,
      contentColor = colors.onAccent
    )
  ) {
    if (saving) {
      SavingIndicator()
    } else {
      Text(save())
    }
  }
}

@Composable
private fun SavingIndicator() {
  CircularProgressIndicator(
    modifier = Modifier.size(SpinnerSize),
    color = colors.onAccent,
    strokeWidth = SaveSpinnerStroke
  )
  Spacer(Modifier.width(Small))
  Text(saving())
}

@PreviewLightDark
@Composable
private fun IdlePreview() {
  WeatherVibeTheme {
    RatingSaveButton(saving = false, enabled = true, onClick = {})
  }
}

@PreviewLightDark
@Composable
private fun SavingPreview() {
  WeatherVibeTheme {
    RatingSaveButton(saving = true, enabled = false, onClick = {})
  }
}

@PreviewLightDark
@Composable
private fun DisabledPreview() {
  WeatherVibeTheme {
    RatingSaveButton(saving = false, enabled = false, onClick = {})
  }
}
