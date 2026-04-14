package com.weather.vibe.feature.widget.config

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.widget.config.state.LocationPickerItemUiState
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Empty
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Ready
import com.weather.vibe.feature.widget.config.ui.WidgetConfigResources
import org.koin.core.annotation.Factory

@Factory
internal class WidgetConfigStateFactory(private val resources: WidgetConfigResources) {

  fun createReadyOrEmpty(locations: List<Location>): WidgetConfigUiState =
    when {
      locations.isEmpty() -> Empty(hint = resources.emptyHint())
      else -> Ready(locations = locations.map(::toItem))
    }

  private fun toItem(location: Location): LocationPickerItemUiState =
    LocationPickerItemUiState(
      id = location.id,
      name = location.name,
      subtitle = resources.formatSubtitle(location.admin1, location.country)
    )
}
