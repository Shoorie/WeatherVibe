package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.widget.repository.PinnedWidgetRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObservePinnedWidgets internal constructor(
  private val repository: PinnedWidgetRepository
) {

  operator fun invoke(): Flow<Map<String, Location>> =
    repository.observeAll()
}
