package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R

internal object HomeForecastTexts {

  @Composable
  fun atmosphereSectionSubtitle(): String =
    stringResource(R.string.atmosphere_section_subtitle)

  @Composable
  fun atmosphereSectionTitle(): String =
    stringResource(R.string.atmosphere_section_title)

  @Composable
  fun conditionsSectionSubtitle(): String =
    stringResource(R.string.conditions_section_subtitle)

  @Composable
  fun conditionsSectionTitle(): String =
    stringResource(R.string.conditions_section_title)

  @Composable
  fun dailyForecastTitle(): String =
    stringResource(R.string.daily_forecast_title)

  @Composable
  fun dayLengthLabel(): String =
    stringResource(R.string.day_length_label)

  @Composable
  fun dayLengthValue(label: String, dayLength: String): String =
    stringResource(R.string.day_length_value_format, label, dayLength)

  @Composable
  fun feelsLikeLabel(temperature: String): String =
    stringResource(R.string.feels_like_label, temperature)

  @Composable
  fun highTempLabel(temperature: String): String =
    stringResource(R.string.high_temp_format, temperature)

  @Composable
  fun hourlyForecastTitle(): String =
    stringResource(R.string.hourly_forecast_title)

  @Composable
  fun lowTempLabel(temperature: String): String =
    stringResource(R.string.low_temp_format, temperature)

  @Composable
  fun searchCityContentDescription(): String =
    stringResource(R.string.search_city_content_description)

  @Composable
  fun settingsContentDescription(): String =
    stringResource(R.string.settings_content_description)

  @Composable
  fun sunSectionSubtitle(): String =
    stringResource(R.string.sun_section_subtitle)

  @Composable
  fun sunSectionTitle(): String =
    stringResource(R.string.sun_section_title)

  @Composable
  fun sunriseLabel(): String =
    stringResource(R.string.sunrise_label)

  @Composable
  fun sunsetLabel(): String =
    stringResource(R.string.sunset_label)

  @Composable
  fun sunriseAt(time: String): String =
    stringResource(R.string.sunrise_at_format, time)

  @Composable
  fun sunsetAt(time: String): String =
    stringResource(R.string.sunset_at_format, time)

  @Composable
  fun sunProgressContentDescription(sunriseTime: String, sunsetTime: String): String =
    stringResource(R.string.sun_progress_content_description, sunriseTime, sunsetTime)

  @Composable
  fun weatherDetailsTitle(): String =
    stringResource(R.string.weather_details_title)

  @Composable
  fun weatherDetailsViewAll(): String =
    stringResource(R.string.weather_details_view_all)

  @Composable
  fun windSectionSubtitle(): String =
    stringResource(R.string.wind_section_subtitle)

  @Composable
  fun windSectionTitle(): String =
    stringResource(R.string.wind_section_title)
}
