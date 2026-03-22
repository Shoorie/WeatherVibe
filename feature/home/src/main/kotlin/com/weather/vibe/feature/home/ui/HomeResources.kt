package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R

internal object HomeResources {

  object Emojis {
    fun cloud(): String = "\u2601\uFE0F"
    fun compass(): String = "\uD83E\uDDED"
    fun dewDrop(): String = "\uD83C\uDF3F"
    fun error(): String = "\u26A1"
    fun eye(): String = "\uD83D\uDC41\uFE0F"
    fun gauge(): String = "\uD83D\uDD36"
    fun humidity(): String = "\uD83D\uDCA7"
    fun locationPin(): String = "\uD83D\uDCCD"
    fun precipitation(): String = "\uD83C\uDF02"
    fun rainfall(): String = "\uD83C\uDF27\uFE0F"
    fun sunrise(): String = "\uD83C\uDF05"
    fun sunset(): String = "\uD83C\uDF07"
    fun uvIndex(): String = "\u2600\uFE0F"
    fun wind(): String = "\uD83D\uDCA8"
    fun windGusts(): String = "\uD83C\uDF2C\uFE0F"
    fun windMax(): String = "\uD83D\uDCA5"
  }

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
    fun searchCityContentDescription(): String =
      stringResource(R.string.search_city_content_description)

    @Composable
    fun refreshContentDescription(): String =
      stringResource(R.string.refresh_content_description)

    @Composable
    fun tryAgainContentDescription(): String =
      stringResource(R.string.try_again_content_description)

    @Composable
    fun feelsLikeLabel(temperature: String): String =
      stringResource(R.string.feels_like_label, temperature)

    @Composable
    fun sunriseLabel(): String =
      stringResource(R.string.sunrise_label)

    @Composable
    fun sunsetLabel(): String =
      stringResource(R.string.sunset_label)

    @Composable
    fun uvIndexLabel(): String =
      stringResource(R.string.uv_index_label)

    @Composable
    fun cloudCoverLabel(): String =
      stringResource(R.string.cloud_cover_label)

    @Composable
    fun dewPointLabel(): String =
      stringResource(R.string.dew_point_label)

    @Composable
    fun pressureLabel(): String =
      stringResource(R.string.pressure_label)

    @Composable
    fun rainfallLabel(): String =
      stringResource(R.string.rainfall_label)

    @Composable
    fun visibilityLabel(): String =
      stringResource(R.string.visibility_label)

    @Composable
    fun windGustsLabel(): String =
      stringResource(R.string.wind_gusts_label)

    @Composable
    fun windSpeedMaxLabel(): String =
      stringResource(R.string.wind_speed_max_label)
  }
}
