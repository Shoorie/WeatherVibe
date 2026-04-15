package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.Text
import com.weather.vibe.feature.widget.glance.preview.sampleMessageStates
import com.weather.vibe.feature.widget.glance.theme.WidgetTextStyles
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState

@Composable
internal fun WidgetMessageLayout(state: WidgetUiState.Message) {
  Column(
    modifier = GlanceModifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = state.emoji,
      style = WidgetTextStyles.messageEmoji
    )
    Spacer(modifier = GlanceModifier.height(SPACING_MD))
    Text(
      text = state.title,
      style = WidgetTextStyles.messageTitle,
      maxLines = 1
    )
    Text(
      text = state.body,
      style = WidgetTextStyles.messageBody,
      maxLines = 3
    )
  }
}

private val SPACING_MD = 6.dp

@PreviewLightDark
@Composable
private fun PreviewWaiting() {
  WidgetMessageLayout(state = sampleMessageStates().waiting)
}

@PreviewLightDark
@Composable
private fun PreviewNoLocation() {
  WidgetMessageLayout(state = sampleMessageStates().noLocation)
}

@PreviewLightDark
@Composable
private fun PreviewError() {
  WidgetMessageLayout(state = sampleMessageStates().error)
}
