package com.weather.vibe.feature.home.ui.component.widgetpromo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewConditionFontSize
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewEmojiSize
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewLocationFontSize
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewMetaFontSize
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewMoodFontSize
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewTemperatureFontSize

@Immutable
internal data class WidgetPromoTextStyles(
  val condition: TextStyle,
  val location: TextStyle,
  val meta: TextStyle,
  val mood: TextStyle,
  val temperature: TextStyle
)

@Composable
internal fun rememberWidgetPromoTextStyles(): WidgetPromoTextStyles {

  val bodySmall = typography.bodySmall
  val bodyMedium = typography.bodyMedium
  val headlineMedium = typography.headlineMedium

  return remember(bodySmall, bodyMedium, headlineMedium) {
    WidgetPromoTextStyles(
      condition = bodyMedium.copy(fontSize = PreviewConditionFontSize),
      location = bodySmall.copy(fontSize = PreviewLocationFontSize),
      meta = bodySmall.copy(fontSize = PreviewMetaFontSize),
      mood = bodySmall.copy(fontSize = PreviewMoodFontSize),
      temperature = headlineMedium.copy(fontSize = PreviewTemperatureFontSize)
    )
  }
}

internal val EmojiTextStyle: TextStyle =
  TextStyle(
    fontSize = PreviewEmojiSize,
    platformStyle = PlatformTextStyle(includeFontPadding = false),
    lineHeightStyle = LineHeightStyle(
      alignment = LineHeightStyle.Alignment.Center,
      trim = LineHeightStyle.Trim.Both
    )
  )
