package com.weather.vibe.feature.home.presentation

import android.graphics.Bitmap
import com.weather.vibe.domain.location.model.Location

internal sealed interface HomeAction {

  data class GenreRemoveClick(val genre: String) : HomeAction

  data class Initialize(val location: Location) : HomeAction

  data class PosterCaptured(val bitmap: Bitmap) : HomeAction

  data object RefreshClick : HomeAction

  data object RetryWeatherSuggestion : HomeAction

  data object ShareClick : HomeAction
}
