package com.weather.vibe.feature.home.presentation.controller

import android.graphics.Bitmap
import com.weather.vibe.core.sharing.ShareBitmapAsImage
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.feature.home.presentation.factory.SharePosterFactory
import com.weather.vibe.feature.home.presentation.state.SharePosterUiState
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory

@Factory
internal class ShareController(
  private val posterFactory: SharePosterFactory,
  private val resources: HomeResources,
  private val shareBitmapAsImage: ShareBitmapAsImage
) {

  fun buildPoster(
    suggestion: WeatherSuggestion,
    unit: TemperatureUnit,
    vibeOneLiner: String?,
    weather: WeatherData
  ): SharePosterUiState =
    posterFactory.create(
      suggestion = suggestion,
      unit = unit,
      vibeOneLiner = vibeOneLiner,
      weather = weather
    )

  suspend fun shareAsImage(bitmap: Bitmap) {
    shareBitmapAsImage(
      bitmap = bitmap,
      chooserTitle = resources.shareChooserTitle()
    )
  }
}
