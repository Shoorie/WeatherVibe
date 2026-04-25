package com.weather.vibe.feature.viberating.presentation.rating

import com.weather.vibe.domain.viberating.model.WeatherSnapshot

internal sealed interface RatingCardAction {

  data class SliderValueChanged(val value: Int) : RatingCardAction

  data class NoteValueChanged(val value: String) : RatingCardAction

  data object NoteExpandClick : RatingCardAction

  data object NoteCollapseClick : RatingCardAction

  data class SaveClick(val weatherSnapshot: WeatherSnapshot) : RatingCardAction

  data class SaveRetryClick(val weatherSnapshot: WeatherSnapshot) : RatingCardAction

  data object DismissErrorClick : RatingCardAction

  data object ViewHistoryClick : RatingCardAction
}
