package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.GlassCardSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeMetric
import com.weather.vibe.core.designsystem.theme.AppDimens.MetricCardHeight
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.preview.MetricPreviewParameterProvider
import com.weather.vibe.feature.home.preview.MetricPreviewParams

@Composable
internal fun WeatherMetricCard(
  modifier: Modifier = Modifier,
  icon: String,
  value: String,
  label: String
) {
  GlassCardSmall(
    modifier = modifier.height(MetricCardHeight)
  ) {
    Text(
      text = icon,
      fontSize = EmojiSizeMetric,
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.weight(1f))
    Text(
      text = value,
      style = typography.titleMedium,
      color = colors.onBackground
    )
    Spacer(modifier = Modifier.height(PaddingExtraSmall))
    Text(
      text = label,
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(MetricPreviewParameterProvider::class)
  params: MetricPreviewParams
) {
  WeatherVibeTheme {
    WeatherMetricCard(
      icon = params.icon,
      value = params.value,
      label = params.label
    )
  }
}
