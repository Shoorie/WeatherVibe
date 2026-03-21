package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.feature.home.preview.DailyWeatherPreviewParameterProvider
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
internal fun DailyForecastItem(
  modifier: Modifier = Modifier,
  dailyWeather: DailyWeather
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = PaddingSmall),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = dailyWeather.date.toDayLabel(),
      style = typography.bodyMedium,
      color = colors.onBackground,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = dailyWeather.condition.emoji,
      fontSize = EmojiSizeSmall,
      modifier = Modifier.weight(1f),
      textAlign = TextAlign.Center
    )
    Row(
      modifier = Modifier.weight(1f),
      horizontalArrangement = Arrangement.End,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "${dailyWeather.maxTemperature.roundToInt()}°",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
      Text(
        text = " / ${dailyWeather.minTemperature.roundToInt()}°",
        style = typography.bodyMedium,
        color = colors.onSurfaceVariant
      )
    }
  }
}

private fun String.toDayLabel(): String = runCatching {
  val date = LocalDate.parse(this)
  if (date == LocalDate.now()) "Today"
  else date.format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))
}.getOrDefault(this)

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyWeatherPreviewParameterProvider::class)
  dailyWeather: DailyWeather
) {
  WeatherVibeTheme {
    DailyForecastItem(dailyWeather = dailyWeather)
  }
}
