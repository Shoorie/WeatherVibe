package com.weather.vibe.feature.search.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class LocationItemUiState(
  val id: Long,
  val name: String,
  val subtitle: String,
  val isFavorite: Boolean,
  val canToggleFavorite: Boolean
)
