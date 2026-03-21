package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R

internal object HomeResources {

  object Texts {

    @Composable
    fun hourlyForecastTitle(): String =
      stringResource(R.string.hourly_forecast_title)

    @Composable
    fun dailyForecastTitle(): String =
      stringResource(R.string.daily_forecast_title)

    @Composable
    fun nowLabel(): String =
      stringResource(R.string.now_label)

    @Composable
    fun humidityLabel(): String =
      stringResource(R.string.humidity_label)

    @Composable
    fun windSpeedLabel(): String =
      stringResource(R.string.wind_speed_label)

    @Composable
    fun directionLabel(): String =
      stringResource(R.string.direction_label)

    @Composable
    fun precipitationLabel(): String =
      stringResource(R.string.precipitation_label)

    @Composable
    fun searchHint(): String =
      stringResource(R.string.search_hint)

    @Composable
    fun searchCityContentDescription(): String =
      stringResource(R.string.search_city_content_description)

    @Composable
    fun refreshContentDescription(): String =
      stringResource(R.string.refresh_content_description)

    @Composable
    fun closeSearchContentDescription(): String =
      stringResource(R.string.close_search_content_description)

    @Composable
    fun tryAgainContentDescription(): String =
      stringResource(R.string.try_again_content_description)

    @Composable
    fun noResultsFound(query: String): String =
      stringResource(R.string.no_results_found, query)
  }
}
