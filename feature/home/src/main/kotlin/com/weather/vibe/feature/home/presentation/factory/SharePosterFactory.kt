package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.model.WeatherVibeKey
import com.weather.vibe.domain.weather.usecase.ResolveWeatherVibeKey
import com.weather.vibe.feature.home.presentation.state.SharePosterUiState
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

@Factory
internal class SharePosterFactory(
  private val resolveWeatherVibeKey: ResolveWeatherVibeKey,
  private val resources: HomeResources,
  private val temperature: TemperatureFormatter,
  private val timeProvider: TimeProvider
) {

  fun create(
    suggestion: WeatherSuggestion,
    unit: TemperatureUnit,
    vibeOneLiner: String?,
    weather: WeatherData
  ): SharePosterUiState {

    val vibeKey = resolveWeatherVibeKey(weather)

    return SharePosterUiState(
      cityName = weather.coordinates.name,
      conditionEmoji = weather.condition.emojiAt(weather.isDay),
      conditionLabel = resources.conditionLabel(weather.condition),
      dateLabel = timeProvider.today().format(dateFormatter),
      gradientKey = VIBE_TO_GRADIENT.getValue(vibeKey),
      outfit = suggestion.outfitSuggestion,
      quoteText = vibeOneLiner ?: suggestion.briefText,
      temperature = temperature.format(celsius = weather.currentTemperature, unit = unit),
      wordmarkHeadline = resources.shareWordmarkHeadline()
    )
  }

  private val dateFormatter: DateTimeFormatter
    get() = ofPattern(DATE_FORMAT, Locale.getDefault())

  private companion object {

    const val DATE_FORMAT = "EEEE, d MMMM"

    val VIBE_TO_GRADIENT: Map<WeatherVibeKey, ShareGradientKey> = mapOf(
      WeatherVibeKey.SUNNY to ShareGradientKey.SUNNY,
      WeatherVibeKey.CLOUDY to ShareGradientKey.CLOUDY,
      WeatherVibeKey.RAINY to ShareGradientKey.RAINY,
      WeatherVibeKey.STORMY to ShareGradientKey.STORMY,
      WeatherVibeKey.SNOWY to ShareGradientKey.SNOWY,
      WeatherVibeKey.NIGHT to ShareGradientKey.NIGHT
    )
  }
}
