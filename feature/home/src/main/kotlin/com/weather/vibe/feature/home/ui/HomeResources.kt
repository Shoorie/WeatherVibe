package com.weather.vibe.feature.home.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.vibe.model.VibeMood
import com.weather.vibe.domain.vibe.model.VibeMood.DREARY
import com.weather.vibe.domain.vibe.model.VibeMood.OKAY
import com.weather.vibe.domain.vibe.model.VibeMood.PLEASANT
import com.weather.vibe.domain.vibe.model.VibeMood.RADIANT
import com.weather.vibe.domain.vibe.model.VibeMood.ROUGH
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.feature.home.R
import org.koin.core.annotation.Factory

@Factory
internal class HomeResources(private val context: Context) {

  fun conditionLabel(condition: WeatherCondition): String =
    context.getString(CONDITION_STRING_IDS.getValue(condition))

  fun cloudCover(): String =
    context.getString(R.string.cloud_cover_label)

  fun dayLengthFormat(hours: Int, minutes: Int): String =
    context.getString(R.string.day_length_format, hours, minutes)

  fun defaultError(): String =
    context.getString(R.string.error_unexpected)

  object WidgetPromo {

    @Composable
    fun title(): String =
      stringResource(R.string.widget_promo_title)

    @Composable
    fun subtitle(): String =
      stringResource(R.string.widget_promo_subtitle)

    @Composable
    fun previewLocation(): String =
      stringResource(R.string.widget_promo_preview_location)

    @Composable
    fun previewCondition(): String =
      stringResource(R.string.widget_promo_preview_condition)

    @Composable
    fun previewMood(): String =
      stringResource(R.string.widget_promo_preview_mood)

    @Composable
    fun previewTemperature(): String =
      stringResource(R.string.widget_promo_preview_temperature)

    @Composable
    fun previewEmoji(): String =
      stringResource(R.string.widget_promo_preview_emoji)

    @Composable
    fun previewFetchedAt(): String =
      stringResource(R.string.widget_promo_preview_fetched_at)

    @Composable
    fun primaryAction(): String =
      stringResource(R.string.widget_promo_primary_action)

    @Composable
    fun secondaryAction(): String =
      stringResource(R.string.widget_promo_secondary_action)
  }

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

  fun nowLabel(): String =
    context.getString(R.string.now_label)

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

  fun shareWordmarkHeadline(): String =
    context.getString(R.string.share_poster_wordmark_headline)

  fun shareChooserTitle(): String =
    context.getString(R.string.share_brief_chooser_title)

  fun dailyVibeSummary(score: Int, mood: VibeMood): String =
    context.getString(
      R.string.daily_vibe_summary_format,
      context.getString(R.string.daily_vibe_score_format, score),
      context.getString(mood.moodLabelRes())
    )

  fun dailyVibeMoodLabel(mood: VibeMood): String =
    context.getString(mood.moodLabelRes())

  fun dailyVibeOneLiner(mood: VibeMood): String =
    context.getString(mood.oneLinerRes())

  fun dailyVibeEmoji(mood: VibeMood): String = when (mood) {
    RADIANT -> HomeEmojis.vibeRadiant()
    PLEASANT -> HomeEmojis.vibePleasant()
    OKAY -> HomeEmojis.vibeOkay()
    DREARY -> HomeEmojis.vibeDreary()
    ROUGH -> HomeEmojis.vibeRough()
  }

  fun dailyVibeContentDescription(mood: VibeMood, score: Int): String =
    context.getString(
      R.string.daily_vibe_content_description_format,
      dailyVibeMoodLabel(mood),
      score,
      dailyVibeOneLiner(mood)
    )

  private fun VibeMood.moodLabelRes(): Int = when (this) {
    RADIANT -> R.string.daily_vibe_mood_radiant
    PLEASANT -> R.string.daily_vibe_mood_pleasant
    OKAY -> R.string.daily_vibe_mood_okay
    DREARY -> R.string.daily_vibe_mood_dreary
    ROUGH -> R.string.daily_vibe_mood_rough
  }

  private fun VibeMood.oneLinerRes(): Int = when (this) {
    RADIANT -> R.string.daily_vibe_oneliner_radiant
    PLEASANT -> R.string.daily_vibe_oneliner_pleasant
    OKAY -> R.string.daily_vibe_oneliner_okay
    DREARY -> R.string.daily_vibe_oneliner_dreary
    ROUGH -> R.string.daily_vibe_oneliner_rough
  }

  private companion object {

    val CONDITION_STRING_IDS = mapOf(
      WeatherCondition.CLEAR_SKY to R.string.condition_clear_sky,
      WeatherCondition.MAINLY_CLEAR to R.string.condition_mainly_clear,
      WeatherCondition.PARTLY_CLOUDY to R.string.condition_partly_cloudy,
      WeatherCondition.OVERCAST to R.string.condition_overcast,
      WeatherCondition.FOG to R.string.condition_fog,
      WeatherCondition.DRIZZLE to R.string.condition_drizzle,
      WeatherCondition.FREEZING_DRIZZLE to R.string.condition_freezing_drizzle,
      WeatherCondition.RAIN to R.string.condition_rain,
      WeatherCondition.FREEZING_RAIN to R.string.condition_freezing_rain,
      WeatherCondition.SNOW to R.string.condition_snow,
      WeatherCondition.RAIN_SHOWERS to R.string.condition_rain_showers,
      WeatherCondition.SNOW_SHOWERS to R.string.condition_snow_showers,
      WeatherCondition.THUNDERSTORM to R.string.condition_thunderstorm,
      WeatherCondition.UNKNOWN to R.string.condition_unknown
    )
  }
}
