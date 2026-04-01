package com.weather.vibe.feature.home.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R
import org.koin.core.annotation.Factory

@Factory
internal class HomeResources(private val context: Context) {

  fun cloudCover(): String =
    context.getString(R.string.cloud_cover_label)

  fun dayLengthFormat(hours: Int, minutes: Int): String =
    context.getString(R.string.day_length_format, hours, minutes)

  fun defaultError(): String =
    context.getString(R.string.error_unexpected)

  fun dewPoint(): String =
    context.getString(R.string.dew_point_label)

  fun direction(): String =
    context.getString(R.string.direction_label)

  fun humidity(): String =
    context.getString(R.string.humidity_label)

  fun precipitation(): String =
    context.getString(R.string.precipitation_label)

  fun pressure(): String =
    context.getString(R.string.pressure_label)

  fun rainfall(): String =
    context.getString(R.string.rainfall_label)

  fun todayLabel(): String =
    context.getString(R.string.today_label)

  fun uvIndex(): String =
    context.getString(R.string.uv_index_label)

  fun visibility(): String =
    context.getString(R.string.visibility_label)

  fun windGusts(): String =
    context.getString(R.string.wind_gusts_label)

  fun windSpeed(): String =
    context.getString(R.string.wind_speed_label)

  fun findingBetterSuggestions(): String =
    context.getString(R.string.finding_better_suggestions)

  fun windSpeedMax(): String =
    context.getString(R.string.wind_speed_max_label)

  object Painters {

    @Composable
    fun musicIcon(): Painter =
      painterResource(id = R.drawable.ic_music)

    @Composable
    fun spotifyIcon(): Painter =
      painterResource(id = R.drawable.ic_spotify)

    @Composable
    fun ytMusicIcon(): Painter =
      painterResource(id = R.drawable.ic_yt_music)
  }

  object Emojis {
    fun cloud(): String = "\u2601\uFE0F"
    fun compass(): String = "\uD83E\uDDED"
    fun dewDrop(): String = "\uD83C\uDF3F"
    fun error(): String = "\u26A1"
    fun eye(): String = "\uD83D\uDC41\uFE0F"
    fun gauge(): String = "\uD83D\uDD36"
    fun humidity(): String = "\uD83D\uDCA7"
    fun locationPin(): String = "\uD83D\uDCCD"
    fun moon(): String = "\uD83C\uDF19"
    fun mostlySunny(): String = "\uD83C\uDF24\uFE0F"
    fun partlyCloudy(): String = "\u26C5"
    fun precipitation(): String = "\uD83C\uDF02"
    fun rainfall(): String = "\uD83C\uDF27\uFE0F"
    fun snow(): String = "\u2744\uFE0F"
    fun sunny(): String = "\u2600\uFE0F"
    fun sunrise(): String = "\uD83C\uDF05"
    fun sunShower(): String = "\uD83C\uDF26\uFE0F"
    fun sunset(): String = "\uD83C\uDF07"
    fun uvIndex(): String = "\u2600\uFE0F"
    fun wind(): String = "\uD83D\uDCA8"
    fun windGusts(): String = "\uD83C\uDF2C\uFE0F"
    fun windMax(): String = "\uD83D\uDCA5"
  }

  object Texts {

    @Composable
    fun aiBriefingLabel(): String =
      stringResource(R.string.ai_briefing_label)

    @Composable
    fun aiBriefingRetryLabel(): String =
      stringResource(R.string.ai_briefing_retry)

    @Composable
    fun aiBriefingUnavailable(): String =
      stringResource(R.string.ai_briefing_unavailable)

    @Composable
    fun atmosphereSectionTitle(): String =
      stringResource(R.string.atmosphere_section_title)

    @Composable
    fun backContentDescription(): String =
      stringResource(R.string.back_content_description)

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
    fun feelsLikeLabel(temperature: String): String =
      stringResource(R.string.feels_like_label, temperature)

    @Composable
    fun genreRemoveContentDescription(genre: String): String =
      stringResource(R.string.genre_remove_content_description, genre)

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
    fun moodPlaylistContentDescription(): String =
      stringResource(R.string.mood_playlist_content_description)

    @Composable
    fun moodPlaylistLabel(): String =
      stringResource(R.string.mood_playlist_label)

    @Composable
    fun moodPlaylistUnavailable(): String =
      stringResource(R.string.mood_playlist_unavailable)

    @Composable
    fun nowLabel(): String =
      stringResource(R.string.now_label)

    @Composable
    fun openInSpotify(): String =
      stringResource(R.string.open_in_spotify)

    @Composable
    fun openInYtMusic(): String =
      stringResource(R.string.open_in_yt_music)

    @Composable
    fun refreshContentDescription(): String =
      stringResource(R.string.refresh_content_description)

    @Composable
    fun searchCityContentDescription(): String =
      stringResource(R.string.search_city_content_description)

    @Composable
    fun settingsContentDescription(): String =
      stringResource(R.string.settings_content_description)

    @Composable
    fun sunriseLabel(): String =
      stringResource(R.string.sunrise_label)

    @Composable
    fun sunsetLabel(): String =
      stringResource(R.string.sunset_label)

    @Composable
    fun tryAgainContentDescription(): String =
      stringResource(R.string.try_again_content_description)

    @Composable
    fun weatherDetailsTitle(): String =
      stringResource(R.string.weather_details_title)

    @Composable
    fun windSectionTitle(): String =
      stringResource(R.string.wind_section_title)
  }
}
