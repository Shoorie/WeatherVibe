package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.text.Text
import com.weather.vibe.feature.widget.glance.preview.WidgetPreview
import com.weather.vibe.feature.widget.glance.theme.WidgetTextStyles
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState

@Composable
internal fun WidgetReadyLayout(state: WidgetReadyUiState) {
  Column(modifier = GlanceModifier.fillMaxSize()) {
    WidgetTopRow(locationName = state.locationName, fetchedAtLabel = state.fetchedAtLabel)
    WidgetHero(emoji = state.conditionEmoji, modifier = GlanceModifier.defaultWeight())
    Spacer(modifier = GlanceModifier.height(4.dp))
    Text(
      modifier = GlanceModifier.fillMaxWidth(),
      text = state.conditionLabel,
      style = WidgetTextStyles.conditionLabel,
      maxLines = 1
    )
    Spacer(modifier = GlanceModifier.height(6.dp))
    WidgetBottomRow(mood = state.mood, temperature = state.temperature)
  }
}

@Composable
private fun WidgetTopRow(locationName: String, fetchedAtLabel: String) {
  Row(
    modifier = GlanceModifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = GlanceModifier.defaultWeight(),
      text = locationName,
      style = WidgetTextStyles.location,
      maxLines = 1
    )
    Text(text = fetchedAtLabel, style = WidgetTextStyles.timestamp, maxLines = 1)
  }
}

@Composable
private fun WidgetHero(emoji: String, modifier: GlanceModifier = GlanceModifier) {
  Box(
    modifier = modifier.fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    Text(text = emoji, style = WidgetTextStyles.hero)
  }
}

@Composable
private fun WidgetBottomRow(mood: String, temperature: String) {
  Row(
    modifier = GlanceModifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = GlanceModifier.defaultWeight(),
      text = mood,
      style = WidgetTextStyles.mood,
      maxLines = 1
    )
    Text(text = temperature, style = WidgetTextStyles.temperature, maxLines = 1)
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WidgetReadyLayout(state = WidgetPreview().sunnyReady)
}
