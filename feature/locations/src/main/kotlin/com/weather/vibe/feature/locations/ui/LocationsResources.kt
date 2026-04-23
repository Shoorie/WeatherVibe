package com.weather.vibe.feature.locations.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.locations.R
import org.koin.core.annotation.Factory

@Factory
internal class LocationsResources(private val context: Context) {

  fun defaultError(): String =
    context.getString(R.string.locations_error_default)

  fun compareError(): String =
    context.getString(R.string.locations_error_compare)

  fun limitReached(limit: Int): String =
    context.getString(R.string.locations_snackbar_limit_reached, limit)

  fun removedSnackbar(name: String): String =
    context.getString(R.string.locations_snackbar_removed, name)

  fun undoAction(): String =
    context.getString(R.string.locations_snackbar_undo)

  object Emojis {
    fun versus(): String = "VS"
    fun metricTemperature(): String = "🌡️"
    fun metricWind(): String = "💨"
    fun metricHumidity(): String = "💧"
    fun metricRain(): String = "☂️"
  }

  object Texts {

    @Composable
    fun headerTitle(): String =
      stringResource(R.string.locations_header_title)

    @Composable
    fun headerSubtitle(count: Int, limit: Int): String = when (count) {
      0 -> stringResource(R.string.locations_header_subtitle_empty)
      1 -> stringResource(R.string.locations_header_subtitle_one, limit)
      else -> stringResource(R.string.locations_header_subtitle, count, limit)
    }

    @Composable
    fun modeBrowse(): String =
      stringResource(R.string.locations_mode_browse)

    @Composable
    fun modeCompare(): String =
      stringResource(R.string.locations_mode_compare)

    @Composable
    fun compareHintPickZero(): String =
      stringResource(R.string.locations_compare_hint_pick_zero)

    @Composable
    fun compareHintPickOne(): String =
      stringResource(R.string.locations_compare_hint_pick_one)

    @Composable
    fun actionAddCity(): String =
      stringResource(R.string.locations_action_add_city)

    @Composable
    fun actionAddCityDisabled(): String =
      stringResource(R.string.locations_action_add_city_disabled)

    @Composable
    fun actionMore(): String =
      stringResource(R.string.locations_action_more)

    @Composable
    fun rowTemperature(value: String): String =
      stringResource(R.string.locations_row_temperature, value)

    @Composable
    fun rowInfoFeels(value: String): String =
      stringResource(R.string.locations_row_info_feels, value)

    @Composable
    fun rowHighLow(high: String, low: String): String =
      stringResource(R.string.locations_row_high_low, high, low)

    @Composable
    fun menuRename(): String =
      stringResource(R.string.locations_menu_rename)

    @Composable
    fun menuDelete(): String =
      stringResource(R.string.locations_menu_delete)

    @Composable
    fun labelSheetTitleRename(): String =
      stringResource(R.string.locations_label_sheet_title_rename)

    @Composable
    fun labelSheetSubtitle(): String =
      stringResource(R.string.locations_label_sheet_subtitle)

    @Composable
    fun labelSheetPlaceholder(): String =
      stringResource(R.string.locations_label_sheet_placeholder)

    @Composable
    fun labelSheetSkip(): String =
      stringResource(R.string.locations_label_sheet_skip)

    @Composable
    fun labelSheetSave(): String =
      stringResource(R.string.locations_label_sheet_save)

    @Composable
    fun labelPresetHome(): String =
      stringResource(R.string.locations_label_sheet_preset_home)

    @Composable
    fun labelPresetWork(): String =
      stringResource(R.string.locations_label_sheet_preset_work)

    @Composable
    fun labelPresetVacation(): String =
      stringResource(R.string.locations_label_sheet_preset_vacation)

    @Composable
    fun labelPresetFamily(): String =
      stringResource(R.string.locations_label_sheet_preset_family)

    @Composable
    fun emptyTitle(): String =
      stringResource(R.string.locations_empty_title)

    @Composable
    fun emptyBody(): String =
      stringResource(R.string.locations_empty_body)

    @Composable
    fun compareTitle(first: String, second: String): String =
      stringResource(R.string.locations_compare_title, first, second)

    @Composable
    fun compareSubtitle(): String =
      stringResource(R.string.locations_compare_subtitle)

    @Composable
    fun timelineTitle(): String =
      stringResource(R.string.locations_compare_timeline_title)

    @Composable
    fun compareFeelsLike(value: String): String =
      stringResource(R.string.locations_compare_feels_like, value)

    @Composable
    fun metricTemperature(): String =
      stringResource(R.string.locations_compare_metric_temperature)

    @Composable
    fun metricWind(): String =
      stringResource(R.string.locations_compare_metric_wind)

    @Composable
    fun metricHumidity(): String =
      stringResource(R.string.locations_compare_metric_humidity)

    @Composable
    fun metricRain(): String =
      stringResource(R.string.locations_compare_metric_rain)

    @Composable
    fun valueTemperature(value: String): String =
      stringResource(R.string.locations_compare_value_temperature, value)

    @Composable
    fun valueWind(kph: Int): String =
      stringResource(R.string.locations_compare_value_wind, kph)

    @Composable
    fun valueHumidity(percent: Int): String =
      stringResource(R.string.locations_compare_value_humidity, percent)

    @Composable
    fun valueRain(percent: Int): String =
      stringResource(R.string.locations_compare_value_rain, percent)
  }
}
