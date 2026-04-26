package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface LocationsHeaderSubtitle {

  @Immutable
  data object CompareHintPickZero : LocationsHeaderSubtitle

  @Immutable
  data object CompareHintPickOne : LocationsHeaderSubtitle

  @Immutable
  data class LocationCount(
    val count: Int,
    val limit: Int
  ) : LocationsHeaderSubtitle
}
