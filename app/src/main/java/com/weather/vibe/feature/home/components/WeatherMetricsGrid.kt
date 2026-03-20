package com.weather.vibe.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme
import kotlin.math.roundToInt

@Composable
fun WeatherMetricsGrid(
    modifier: Modifier = Modifier,
    humidity: Int,
    windSpeed: Double,
    windDirection: Double,
    precipitationProbability: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.PaddingSmall)
    ) {
        WeatherMetricCard(
            modifier = Modifier.weight(1f),
            icon = "💧",
            value = "$humidity%",
            label = "Humidity"
        )
        WeatherMetricCard(
            modifier = Modifier.weight(1f),
            icon = "💨",
            value = "${windSpeed.roundToInt()} km/h",
            label = "Wind Speed"
        )
        WeatherMetricCard(
            modifier = Modifier.weight(1f),
            icon = "🧭",
            value = windDirection.toCardinalDirection(),
            label = "Direction"
        )
        WeatherMetricCard(
            modifier = Modifier.weight(1f),
            icon = "🌂",
            value = "$precipitationProbability%",
            label = "Precipitation"
        )
    }
}

private fun Double.toCardinalDirection(): String {
    val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
    val index = ((this / 45.0) + 0.5).toInt() % 8
    return directions[index]
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun WeatherMetricsGridPreview() {
    WeatherVibeTheme {
        WeatherMetricsGrid(
            humidity = 65,
            windSpeed = 14.5,
            windDirection = 225.0,
            precipitationProbability = 20
        )
    }
}
