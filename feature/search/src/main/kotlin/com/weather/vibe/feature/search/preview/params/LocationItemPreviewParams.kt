package com.weather.vibe.feature.search.preview.params

import androidx.compose.runtime.Immutable

@Immutable
internal data class LocationItemPreviewParams(
  val emoji: String,
  val name: String,
  val subtitle: String,
  val isFavorite: Boolean = false
)
