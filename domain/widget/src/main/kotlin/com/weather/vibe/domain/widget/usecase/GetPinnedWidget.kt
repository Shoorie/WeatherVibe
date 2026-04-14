package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.widget.repository.PinnedWidgetRepository
import org.koin.core.annotation.Factory

@Factory
class GetPinnedWidget internal constructor(
  private val repository: PinnedWidgetRepository
) {

  suspend operator fun invoke(glanceId: String): Location? =
    repository.get(glanceId)
}
