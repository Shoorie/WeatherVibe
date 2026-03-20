package com.weather.vibe.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.weather.vibe.domain.model.HourlyWeather
import com.weather.vibe.domain.model.WeatherCondition
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun HourlyForecastItem(
    modifier: Modifier = Modifier,
    hourlyWeather: HourlyWeather,
    isCurrentHour: Boolean = false
) {
    Column(
        modifier = modifier
            .width(AppDimens.HourlyItemWidth)
            .height(AppDimens.HourlyItemHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = if (isCurrentHour) "Now" else hourlyWeather.time.toHourLabel(),
            style = MaterialTheme.typography.labelSmall,
            color = if (isCurrentHour) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            textAlign = TextAlign.Center
        )
        Text(
            text = hourlyWeather.condition.emoji,
            fontSize = 22.sp
        )
        Text(
            text = "${hourlyWeather.temperature.roundToInt()}°",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}

private fun String.toHourLabel(): String = runCatching {
    val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
    dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}.getOrDefault(this)

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun HourlyForecastItemPreview() {
    WeatherVibeTheme {
        HourlyForecastItem(
            hourlyWeather = HourlyWeather(
                time = "2024-01-15T14:00",
                temperature = 21.0,
                condition = WeatherCondition.PARTLY_CLOUDY,
                humidity = 60,
                windSpeed = 10.0,
                precipitationProbability = 15
            ),
            isCurrentHour = false
        )
    }
}
