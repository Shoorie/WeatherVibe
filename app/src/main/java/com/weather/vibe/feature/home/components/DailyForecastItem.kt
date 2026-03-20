package com.weather.vibe.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.weather.vibe.domain.model.DailyWeather
import com.weather.vibe.domain.model.WeatherCondition
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DailyForecastItem(
    modifier: Modifier = Modifier,
    dailyWeather: DailyWeather
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppDimens.PaddingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dailyWeather.date.toDayLabel(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = dailyWeather.condition.emoji,
            fontSize = 20.sp,
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = " / ${dailyWeather.minTemperature.roundToInt()}°",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun String.toDayLabel(): String = runCatching {
    val date = LocalDate.parse(this)
    if (date == LocalDate.now()) "Today"
    else date.format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))
}.getOrDefault(this)

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun DailyForecastItemPreview() {
    WeatherVibeTheme {
        DailyForecastItem(
            dailyWeather = DailyWeather(
                date = "2024-01-16",
                maxTemperature = 22.0,
                minTemperature = 14.0,
                condition = WeatherCondition.PARTLY_CLOUDY,
                precipitationProbability = 20
            )
        )
    }
}
