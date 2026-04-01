package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class GenreChipUiState(
  val isRejecting: Boolean = false,
  val name: String
)
