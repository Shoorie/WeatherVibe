package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.Text
import com.weather.vibe.feature.widget.glance.preview.sampleMessageStates
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Message
import com.weather.vibe.feature.widget.ui.theme.WidgetDimens
import com.weather.vibe.feature.widget.ui.theme.WidgetTextStyles

@Composable
internal fun WidgetMessageLayout(state: Message) {
  Column(
    modifier = GlanceModifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = state.message.emoji,
      style = WidgetTextStyles.messageEmoji
    )
    Spacer(modifier = GlanceModifier.height(WidgetDimens.spacingMedium))
    Text(
      text = state.message.title,
      style = WidgetTextStyles.messageTitle,
      maxLines = 1
    )
    Text(
      text = state.message.body,
      style = WidgetTextStyles.messageBody,
      maxLines = 3
    )
  }
}

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
