package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
import com.weather.vibe.feature.widget.glance.preview.sampleWeatherState
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Weather
import com.weather.vibe.feature.widget.ui.theme.WidgetDimens
import com.weather.vibe.feature.widget.ui.theme.WidgetTextStyles

@Composable
internal fun WidgetWeatherLayout(state: Weather) {
  Column(modifier = GlanceModifier.fillMaxSize()) {
    WidgetWeatherHeader(
      locationName = state.locationName,
      fetchedAtLabel = state.fetchedAtLabel
    )
    WidgetWeatherShowcase(
      modifier = GlanceModifier.defaultWeight(),
      conditionEmoji = state.conditionEmoji,
      conditionLabel = state.conditionLabel,
      temperature = state.temperature
    )
    Spacer(modifier = GlanceModifier.height(WidgetDimens.spacingMedium))
    WidgetWeatherFooter(mood = state.mood)
  }
}

@Composable
private fun WidgetWeatherHeader(
  locationName: String,
  fetchedAtLabel: String
) {
  Row(
    modifier = GlanceModifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = GlanceModifier.defaultWeight(),
      text = locationName,
      style = WidgetTextStyles.locationName,
      maxLines = 1
    )
    Text(
      text = fetchedAtLabel,
      style = WidgetTextStyles.fetchedAtLabel,
      maxLines = 1
    )
  }
}

@Composable
private fun WidgetWeatherShowcase(
  conditionEmoji: String,
  conditionLabel: String,
  temperature: String,
  modifier: GlanceModifier = GlanceModifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = conditionEmoji,
      style = WidgetTextStyles.conditionEmoji,
      maxLines = 1
    )
    Text(
      text = temperature,
      style = WidgetTextStyles.temperature,
      maxLines = 1
    )
    Spacer(modifier = GlanceModifier.height(WidgetDimens.spacingSmall))
    Text(
      text = conditionLabel,
      style = WidgetTextStyles.conditionLabel,
      maxLines = 1
    )
  }
}

@Composable
private fun WidgetWeatherFooter(mood: String) {
  Box(
    modifier = GlanceModifier.fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = mood,
      style = WidgetTextStyles.mood,
      maxLines = 1
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WidgetWeatherLayout(state = sampleWeatherState())
}
