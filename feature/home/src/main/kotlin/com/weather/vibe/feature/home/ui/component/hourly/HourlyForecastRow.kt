package com.weather.vibe.feature.home.ui.component.hourly

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabelText
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.preview.HourlyForecastListPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.hourlyForecastTitle

@Composable
internal fun HourlyForecastRow(
  modifier: Modifier = Modifier,
  state: HourlyForecastsUiState
) {

  val listState = rememberLazyListState()

  LaunchedEffect(state.items.firstOrNull()?.timeLabel) {
    listState.animateScrollToItem(index = 0)
  }

  Column(modifier = modifier.fillMaxWidth()) {
    SectionLabelText(
      modifier = Modifier
        .padding(horizontal = Medium),
      text = hourlyForecastTitle(),
      uppercase = true
    )
    LazyRow(
      state = listState,
      horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
      contentPadding = PaddingValues(horizontal = Medium)
    ) {
      items(
        items = state.items,
        key = { it.timeLabel }
      ) { hourly ->
        HourlyForecastItem(state = hourly)
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HourlyForecastListPreview::class)
  state: HourlyForecastsUiState
) {
  WeatherVibeTheme {
    HourlyForecastRow(state = state)
  }
}
