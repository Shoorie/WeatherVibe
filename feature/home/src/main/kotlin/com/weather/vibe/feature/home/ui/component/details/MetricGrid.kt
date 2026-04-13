package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeDefaults.MetricGridColumns
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.precipitation
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.uvIndex
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind

@Composable
internal fun MetricGrid(
  modifier: Modifier = Modifier,
  items: List<MetricItemUiState>
) {
  val rowsOfTwo = remember(items) { items.chunked(MetricGridColumns) }
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    rowsOfTwo.forEach { row ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Padding.Small)
      ) {
        row.forEach { item ->
          MetricTile(modifier = Modifier.weight(1f), item = item)
        }
        if (row.size < MetricGridColumns) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    MetricGrid(
      modifier = Modifier.padding(Padding.Medium),
      items = listOf(
        MetricItemUiState(humidity(), "Humidity", "65%"),
        MetricItemUiState(precipitation(), "Precipitation", "20%"),
        MetricItemUiState(uvIndex(), "UV Index", "3.5"),
        MetricItemUiState(wind(), "Wind Speed", "12 km/h")
      )
    )
  }
}
