package com.weather.vibe.feature.home.presentation

internal sealed interface HomeAction {

  data class ReceiveLocationResult(
    val cityName: String,
    val latitude: Double,
    val longitude: Double
  ) : HomeAction

  data object RefreshClick : HomeAction
}
