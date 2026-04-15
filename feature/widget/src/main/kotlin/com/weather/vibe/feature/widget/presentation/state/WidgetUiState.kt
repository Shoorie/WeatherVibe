package com.weather.vibe.feature.widget.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
sealed interface WidgetUiState {

  val locationId: Long?

  @Immutable
  data class Weather(
    val conditionEmoji: String,
    val conditionLabel: String,
    val contentDescription: String,
    val fetchedAtLabel: String,
    override val locationId: Long,
    val locationName: String,
    val mood: String,
    val temperature: String
  ) : WidgetUiState

  @Immutable
  sealed interface Message : WidgetUiState {

    val body: String
    val emoji: String
    val title: String

    override val locationId: Long? get() = null
  }

  @Immutable
  data class Waiting(
    override val body: String,
    override val emoji: String,
    override val title: String
  ) : Message

  @Immutable
  data class NoLocation(
    override val body: String,
    override val emoji: String,
    override val title: String
  ) : Message

  @Immutable
  data class Error(
    override val body: String,
    override val emoji: String,
    override val title: String
  ) : Message
}
