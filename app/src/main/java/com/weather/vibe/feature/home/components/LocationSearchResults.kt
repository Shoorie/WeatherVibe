package com.weather.vibe.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.weather.vibe.domain.model.LocationResult
import com.weather.vibe.ui.components.GlassCard
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.WeatherVibeTheme

@Composable
fun LocationSearchResults(
    modifier: Modifier = Modifier,
    results: List<LocationResult>,
    onLocationSelected: (LocationResult) -> Unit
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        results.forEachIndexed { index, result ->
            LocationResultItem(
                result = result,
                onSelected = onLocationSelected
            )
            if (index < results.lastIndex) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
private fun LocationResultItem(
    modifier: Modifier = Modifier,
    result: LocationResult,
    onSelected: (LocationResult) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelected(result) }
            .padding(vertical = AppDimens.PaddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = result.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            val subtitle = buildSubtitle(result.admin1, result.country)
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = "📍",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun buildSubtitle(admin1: String?, country: String): String = buildString {
    if (!admin1.isNullOrEmpty()) append(admin1)
    if (country.isNotEmpty()) {
        if (!admin1.isNullOrEmpty()) append(", ")
        append(country)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun LocationSearchResultsPreview() {
    WeatherVibeTheme {
        LocationSearchResults(
            results = listOf(
                LocationResult(1L, "Warszawa", 52.229, 21.011, "Polska", "Masovian Voivodeship"),
                LocationResult(2L, "Wrocław", 51.107, 17.038, "Polska", "Lower Silesian Voivodeship"),
                LocationResult(3L, "Kraków", 50.061, 19.937, "Polska", "Lesser Poland Voivodeship")
            ),
            onLocationSelected = {}
        )
    }
}
