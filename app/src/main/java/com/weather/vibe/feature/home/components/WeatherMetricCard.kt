package com.weather.vibe.feature.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.weather.vibe.ui.components.GlassCardSmall
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme

@Composable
fun WeatherMetricCard(
    modifier: Modifier = Modifier,
    icon: String,
    value: String,
    label: String
) {
    GlassCardSmall(
        modifier = modifier.height(AppDimens.MetricCardHeight)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(AppDimens.PaddingExtraSmall))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun WeatherMetricCardPreview() {
    WeatherVibeTheme {
        WeatherMetricCard(
            icon = "💧",
            value = "65%",
            label = "Humidity"
        )
    }
}
