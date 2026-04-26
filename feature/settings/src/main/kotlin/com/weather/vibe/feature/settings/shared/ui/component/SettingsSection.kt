package com.weather.vibe.feature.settings.shared.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.label.SectionHeader
import com.weather.vibe.core.designsystem.components.toggle.VibeSwitch
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
internal fun SettingsSection(
  modifier: Modifier = Modifier,
  emoji: String,
  title: String,
  subtitle: String,
  toggle: SettingsToggle? = null,
  content: (@Composable ColumnScope.() -> Unit)? = null
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.surfaceVariant)
      .border(Stroke.Border, colors.outlineVariant, shapes.card)
      .toggleSectionModifier(toggle)
      .padding(Padding.Large)
  ) {
    SectionHeaderRow(
      emoji = emoji,
      title = title,
      subtitle = subtitle,
      toggle = toggle
    )
    if (content != null) {
      Spacer(modifier = Modifier.height(Medium))
      content()
    }
  }
}

@Composable
private fun SectionHeaderRow(
  emoji: String,
  title: String,
  subtitle: String,
  toggle: SettingsToggle?
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Medium),
    verticalAlignment = Alignment.CenterVertically
  ) {
    SectionHeader(
      modifier = Modifier.weight(1f),
      emoji = emoji,
      title = title,
      subtitle = subtitle
    )
    if (toggle != null) {
      VibeSwitch(checked = toggle.checked)
    }
  }
}

private fun Modifier.toggleSectionModifier(toggle: SettingsToggle?): Modifier =
  if (toggle == null) this
  else this
    .semantics(mergeDescendants = true) { stateDescription = toggle.stateLabel }
    .toggleable(
      value = toggle.checked,
      role = Role.Switch,
      onValueChange = toggle.onChange
    )

@PreviewLightDark
@Composable
private fun PreviewWithContent() {
  WeatherVibeTheme {
    SettingsSection(
      modifier = Modifier.padding(Medium),
      emoji = "🌡️",
      title = "Section title",
      subtitle = "Subtitle explains what this does"
    ) {
      Text(
        text = "Section content",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun PreviewWithToggle() {
  WeatherVibeTheme {
    SettingsSection(
      modifier = Modifier.padding(Medium),
      emoji = "⚠️",
      title = "Weather alerts",
      subtitle = "Proactive heads-up when the weather is about to turn",
      toggle = SettingsToggle(
        checked = true,
        onChange = {},
        stateLabel = "On"
      )
    )
  }
}
