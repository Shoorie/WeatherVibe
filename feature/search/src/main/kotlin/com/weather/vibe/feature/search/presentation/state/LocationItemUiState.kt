package com.weather.vibe.feature.search.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class LocationItemUiState(
  val admin1: String?,
  val country: String,
  val id: Long,
  val latitude: Double,
  val longitude: Double,
  val name: String,
  val subtitle: String,
  val temperature: String? = null
)
