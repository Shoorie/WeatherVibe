package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.weather.model.LocationResult
import com.weather.vibe.feature.home.ui.HomeResources.Emojis
import com.weather.vibe.feature.home.preview.SearchResultsPreview

@Composable
internal fun LocationSearchResults(
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
        HorizontalDivider(color = colors.outline)
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
      .padding(vertical = PaddingSmall),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = result.name,
        style = typography.bodyMedium,
        color = colors.onBackground
      )
      val subtitle = buildSubtitle(result.admin1, result.country)
      if (subtitle.isNotEmpty()) {
        Text(
          text = subtitle,
          style = typography.bodySmall,
          color = colors.onSurfaceVariant
        )
      }
    }
    Text(
      text = Emojis.locationPin(),
      style = typography.bodyMedium
    )
  }
}

// TODO [azalewski on 21/03/2026]: This logic should not be there.
private fun buildSubtitle(admin1: String?, country: String): String =
  buildString {
    if (!admin1.isNullOrEmpty()) append(admin1)
    if (country.isNotEmpty()) {
      if (!admin1.isNullOrEmpty()) append(", ")
      append(country)
    }
  }

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SearchResultsPreview::class)
  results: List<LocationResult>
) {
  WeatherVibeTheme {
    LocationSearchResults(
      results = results,
      onLocationSelected = {}
    )
  }
}
