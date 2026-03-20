package com.weather.vibe.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.weather.vibe.domain.model.WeatherData
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherSection(
    modifier: Modifier = Modifier,
    weatherData: WeatherData
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppDimens.PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = weatherData.condition.emoji,
            fontSize = 96.sp
        )

        Spacer(modifier = Modifier.height(AppDimens.PaddingSmall))

        Text(
            text = "${weatherData.currentTemperature.roundToInt()}°",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(AppDimens.PaddingExtraSmall))

        Text(
            text = weatherData.condition.label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(AppDimens.PaddingMedium))

        weatherData.dailyForecast.firstOrNull()?.let { today ->
            HighLowTemperatureRow(today = today)
        }
    }
}

@Composable
private fun HighLowTemperatureRow(
    modifier: Modifier = Modifier,
    today: DailyWeather
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.PaddingMedium)
    ) {
        Text(
            text = "H: ${today.maxTemperature.roundToInt()}°",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "L: ${today.minTemperature.roundToInt()}°",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun CurrentWeatherSectionPreview() {
    WeatherVibeTheme {
        CurrentWeatherSection(
            weatherData = WeatherData(
                cityName = "Zielona Góra",
                latitude = 51.9354,
                longitude = 15.5064,
                currentTemperature = 18.5,
                condition = WeatherCondition.PARTLY_CLOUDY,
                windSpeed = 12.0,
                windDirection = 225.0,
                humidity = 65,
                isDay = true,
                hourlyForecast = emptyList(),
                dailyForecast = listOf(
                    DailyWeather("2024-01-15", 22.0, 14.0, WeatherCondition.PARTLY_CLOUDY, 20)
                )
            )
        )
    }
}
