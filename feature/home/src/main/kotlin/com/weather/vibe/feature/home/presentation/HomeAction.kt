package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.location.model.Location

internal sealed interface HomeAction {

  data class GenreRemoveClick(val genre: String) : HomeAction

  data class Initialize(val location: Location?) : HomeAction

  data object RefreshClick : HomeAction

  data object ResumeLifecycle : HomeAction

  data object RetryWeatherSuggestion : HomeAction
}
