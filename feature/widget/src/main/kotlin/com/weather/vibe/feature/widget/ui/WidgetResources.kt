package com.weather.vibe.feature.widget.ui

import android.content.Context
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.FOG
import com.weather.vibe.domain.weather.model.WeatherCondition.FREEZING_DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.FREEZING_RAIN
import com.weather.vibe.domain.weather.model.WeatherCondition.MAINLY_CLEAR
import com.weather.vibe.domain.weather.model.WeatherCondition.OVERCAST
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN_SHOWERS
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW_SHOWERS
import com.weather.vibe.domain.weather.model.WeatherCondition.THUNDERSTORM
import com.weather.vibe.domain.weather.model.WeatherCondition.UNKNOWN
import com.weather.vibe.feature.widget.R
import org.koin.core.annotation.Factory

@Factory
internal class WidgetResources(private val context: Context) {

  fun noLocationTitle(): String =
    context.getString(R.string.widget_no_location_title)

  fun noLocationBody(): String =
    context.getString(R.string.widget_no_location_body)

  fun waitingTitle(): String =
    context.getString(R.string.widget_waiting_title)

  fun waitingBody(locationName: String): String =
    context.getString(R.string.widget_waiting_body, locationName)

  fun errorTitle(): String =
    context.getString(R.string.widget_error_title)

  fun errorBody(): String =
    context.getString(R.string.widget_error_body)

  fun temperature(degrees: Int): String =
    context.getString(R.string.widget_temperature_format, degrees)

  fun weatherContentDescription(locationName: String, mood: String): String =
    context.getString(R.string.widget_weather_content_description, locationName, mood)

  fun conditionLabel(condition: WeatherCondition): String =
    context.getString(CONDITION_STRING_IDS.getValue(condition))

  private companion object {

    val CONDITION_STRING_IDS = mapOf(
      CLEAR_SKY to R.string.widget_condition_clear_sky,
      MAINLY_CLEAR to R.string.widget_condition_mainly_clear,
      PARTLY_CLOUDY to R.string.widget_condition_partly_cloudy,
      OVERCAST to R.string.widget_condition_overcast,
      FOG to R.string.widget_condition_fog,
      DRIZZLE to R.string.widget_condition_drizzle,
      FREEZING_DRIZZLE to R.string.widget_condition_freezing_drizzle,
      RAIN to R.string.widget_condition_rain,
      FREEZING_RAIN to R.string.widget_condition_freezing_rain,
      SNOW to R.string.widget_condition_snow,
      RAIN_SHOWERS to R.string.widget_condition_rain_showers,
      SNOW_SHOWERS to R.string.widget_condition_snow_showers,
      THUNDERSTORM to R.string.widget_condition_thunderstorm,
      UNKNOWN to R.string.widget_condition_unknown
    )
  }
}
