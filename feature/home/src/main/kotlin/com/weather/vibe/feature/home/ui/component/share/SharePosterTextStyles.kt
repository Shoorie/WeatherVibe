package com.weather.vibe.feature.home.ui.component.share

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeaderLetterSpacing
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeroEmojiSize
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeroTemperatureLetterSpacing
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeroTemperatureSize
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.QuoteLineHeight
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.QuoteTextSize
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.WordmarkLetterSpacing
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.WordmarkSize

@Composable
internal fun posterCityStyle(): TextStyle {
  val base = typography.titleLarge
  return remember(base) {
    base.copy(
      fontWeight = FontWeight.SemiBold,
      letterSpacing = HeaderLetterSpacing
    )
  }
}

@Composable
internal fun posterDateStyle(): TextStyle {
  val base = typography.labelMedium
  return remember(base) { base.copy(letterSpacing = HeaderLetterSpacing) }
}

@Composable
internal fun posterEmojiStyle(): TextStyle {
  val base = typography.displayLarge
  return remember(base) { base.copy(fontSize = HeroEmojiSize) }
}

@Composable
internal fun posterTemperatureStyle(): TextStyle {
  val base = typography.displayLarge
  return remember(base) {
    base.copy(
      fontSize = HeroTemperatureSize,
      fontWeight = FontWeight.Bold,
      letterSpacing = HeroTemperatureLetterSpacing
    )
  }
}

@Composable
internal fun posterConditionStyle(): TextStyle {
  val base = typography.titleMedium
  return remember(base) { base.copy(fontWeight = FontWeight.Medium) }
}

@Composable
internal fun posterQuoteStyle(): TextStyle {
  val base = typography.titleMedium
  return remember(base) {
    base.copy(
      fontSize = QuoteTextSize,
      fontStyle = FontStyle.Italic,
      fontWeight = FontWeight.Medium,
      lineHeight = QuoteLineHeight
    )
  }
}

@Composable
internal fun posterOutfitStyle(): TextStyle {
  val base = typography.labelMedium
  return remember(base) { base.copy(fontWeight = FontWeight.Medium) }
}

@Composable
internal fun posterWordmarkStyle(): TextStyle {
  val base = typography.labelSmall
  return remember(base) {
    base.copy(
      fontSize = WordmarkSize,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = WordmarkLetterSpacing
    )
  }
}
