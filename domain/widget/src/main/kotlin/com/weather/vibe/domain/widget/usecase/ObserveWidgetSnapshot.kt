package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveWidgetSnapshot internal constructor(
  private val repository: WidgetSnapshotRepository
) {

  operator fun invoke(locationId: Long): Flow<WidgetSnapshot?> =
    repository.observe(locationId)
}
