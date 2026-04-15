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
import com.weather.vibe.feature.widget.glance.preview.sampleWeatherState
import com.weather.vibe.feature.widget.glance.theme.WidgetTextStyles
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState

@Composable
internal fun WidgetWeatherLayout(state: WidgetUiState.Weather) {
  Column(modifier = GlanceModifier.fillMaxSize()) {
    WidgetWeatherHeader(
      locationName = state.locationName,
      fetchedAtLabel = state.fetchedAtLabel
    )
    WidgetConditionIcon(
      emoji = state.conditionEmoji,
      modifier = GlanceModifier.defaultWeight()
    )
    Spacer(modifier = GlanceModifier.height(SPACING_SM))
    Text(
      modifier = GlanceModifier.fillMaxWidth(),
      text = state.conditionLabel,
      style = WidgetTextStyles.conditionLabel,
      maxLines = 1
    )
    Spacer(modifier = GlanceModifier.height(SPACING_MD))
    WidgetWeatherFooter(
      mood = state.mood,
      temperature = state.temperature
    )
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
private fun WidgetConditionIcon(
  emoji: String,
  modifier: GlanceModifier = GlanceModifier
) {
  Box(
    modifier = modifier.fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    Text(text = emoji, style = WidgetTextStyles.conditionEmoji)
  }
}

@Composable
private fun WidgetWeatherFooter(
  mood: String,
  temperature: String
) {
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
    Text(
      text = temperature,
      style = WidgetTextStyles.temperature,
      maxLines = 1
    )
  }
}

private val SPACING_SM = 4.dp
private val SPACING_MD = 6.dp

@PreviewLightDark
@Composable
private fun Preview() {
  WidgetWeatherLayout(state = sampleWeatherState())
}
