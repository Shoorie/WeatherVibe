package com.weather.vibe.feature.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.weather.vibe.domain.model.DailyWeather
import com.weather.vibe.domain.model.WeatherCondition
import com.weather.vibe.ui.components.GlassCard
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme

@Composable
fun DailyForecastList(
    modifier: Modifier = Modifier,
    dailyForecasts: List<DailyWeather>
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "7-Day Forecast",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(AppDimens.PaddingSmall))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        dailyForecasts.forEachIndexed { index, daily ->
            DailyForecastItem(dailyWeather = daily)
            if (index < dailyForecasts.lastIndex) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun DailyForecastListPreview() {
    WeatherVibeTheme {
        DailyForecastList(
            dailyForecasts = listOf(
                DailyWeather("2024-01-15", 22.0, 14.0, WeatherCondition.PARTLY_CLOUDY, 20),
                DailyWeather("2024-01-16", 19.0, 11.0, WeatherCondition.RAIN, 75),
                DailyWeather("2024-01-17", 15.0, 8.0, WeatherCondition.OVERCAST, 30),
                DailyWeather("2024-01-18", 24.0, 16.0, WeatherCondition.CLEAR_SKY, 5),
                DailyWeather("2024-01-19", 21.0, 13.0, WeatherCondition.MAINLY_CLEAR, 10),
                DailyWeather("2024-01-20", 17.0, 10.0, WeatherCondition.DRIZZLE, 60),
                DailyWeather("2024-01-21", 20.0, 12.0, WeatherCondition.PARTLY_CLOUDY, 25)
            )
        )
    }
}
