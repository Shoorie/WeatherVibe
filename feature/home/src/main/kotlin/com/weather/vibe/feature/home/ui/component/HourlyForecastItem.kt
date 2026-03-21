package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.HourlyItemHeight
import com.weather.vibe.core.designsystem.theme.AppDimens.HourlyItemWidth
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.feature.home.preview.HourlyWeatherPreviewParameterProvider
import com.weather.vibe.feature.home.ui.HomeResources.Texts.nowLabel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
internal fun HourlyForecastItem(
  modifier: Modifier = Modifier,
  hourlyWeather: HourlyWeather,
  isCurrentHour: Boolean = false
) {
  Column(
    modifier = modifier
      .width(HourlyItemWidth)
      .height(HourlyItemHeight),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(
      text = if (isCurrentHour) nowLabel()
        else hourlyWeather.time.toHourLabel(),
      style = typography.labelSmall,
      color = if (isCurrentHour) colors.accent else colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
    Text(
      text = hourlyWeather.condition.emoji,
      fontSize = EmojiSizeMedium
    )
    Text(
      text = "${hourlyWeather.temperature.roundToInt()}°",
      style = typography.bodyMedium,
      color = colors.onBackground,
      textAlign = TextAlign.Center
    )
  }
}

private fun String.toHourLabel(): String = runCatching {
  val dateTime = LocalDateTime.parse(
    this,
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
  )
  dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}.getOrDefault(this)

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HourlyWeatherPreviewParameterProvider::class)
  hourlyWeather: HourlyWeather
) {
  WeatherVibeTheme {
    HourlyForecastItem(
      hourlyWeather = hourlyWeather,
      isCurrentHour = false
    )
  }
}
