package com.weather.vibe.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.weather.vibe.domain.model.HourlyWeather
import com.weather.vibe.domain.model.WeatherCondition
import com.weather.vibe.ui.components.GlassCard
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme

@Composable
fun HourlyForecastRow(
    modifier: Modifier = Modifier,
    hourlyForecasts: List<HourlyWeather>
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Hourly Forecast",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(AppDimens.PaddingSmall))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(AppDimens.PaddingSmall))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(AppDimens.PaddingExtraSmall),
            contentPadding = PaddingValues(horizontal = AppDimens.PaddingExtraSmall)
        ) {
            itemsIndexed(hourlyForecasts) { index, hourly ->
                HourlyForecastItem(
                    hourlyWeather = hourly,
                    isCurrentHour = index == 0
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun HourlyForecastRowPreview() {
    WeatherVibeTheme {
        HourlyForecastRow(
            hourlyForecasts = List(8) { index ->
                HourlyWeather(
                    time = "2024-01-15T${14 + index}:00",
                    temperature = 18.0 + index,
                    condition = WeatherCondition.entries[index % WeatherCondition.entries.size],
                    humidity = 60 + index,
                    windSpeed = 10.0,
                    precipitationProbability = index * 5
                )
            }
        )
    }
}
