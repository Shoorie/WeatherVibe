package com.weather.vibe.feature.home.ui.component.widgetpromo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.ui.HomeWidgetPromoTexts
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewCornerRadius
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewHeight
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewMinWidth
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoDefaults.PreviewPadding

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun WidgetPromoMiniPreview(modifier: Modifier = Modifier) {

  val styles = rememberWidgetPromoTextStyles()

  Column(
    modifier = modifier
      .defaultMinSize(minWidth = PreviewMinWidth)
      .height(PreviewHeight)
      .clip(RoundedCornerShape(PreviewCornerRadius))
      .background(colors.cardContainer)
      .padding(PreviewPadding)
      .semantics(mergeDescendants = true) { invisibleToUser() }
  ) {
    PreviewHeader(styles = styles)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = HomeWidgetPromoTexts.previewEmoji(),
        style = EmojiTextStyle
      )
    }
    PreviewFooter(styles = styles)
  }
}

@Composable
private fun PreviewHeader(styles: WidgetPromoTextStyles) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = HomeWidgetPromoTexts.previewLocation(),
      style = styles.location,
      color = colors.onSurface,
      fontWeight = FontWeight.SemiBold
    )
    Text(
      text = HomeWidgetPromoTexts.previewFetchedAt(),
      style = styles.meta,
      color = colors.onSurfaceVariant
    )
  }
}

@Composable
private fun PreviewFooter(styles: WidgetPromoTextStyles) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Bottom
  ) {
    Column {
      Text(
        text = HomeWidgetPromoTexts.previewCondition(),
        style = styles.condition,
        color = colors.onSurface,
        fontWeight = FontWeight.SemiBold
      )
      Text(
        text = HomeWidgetPromoTexts.previewMood(),
        style = styles.mood,
        color = colors.onSurfaceVariant
      )
    }
    Text(
      text = HomeWidgetPromoTexts.previewTemperature(),
      style = styles.temperature,
      color = colors.onSurface,
      fontWeight = FontWeight.Bold
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WidgetPromoMiniPreview(modifier = Modifier.padding(Medium))
  }
}
