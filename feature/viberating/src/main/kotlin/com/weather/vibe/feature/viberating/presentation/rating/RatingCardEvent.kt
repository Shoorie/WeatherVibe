package com.weather.vibe.feature.viberating.presentation.rating

internal sealed interface RatingCardEvent {

  data object NavigateToHistory : RatingCardEvent
}
