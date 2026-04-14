package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.domain.widget.repository.PinnedWidgetRepository
import org.koin.core.annotation.Factory

@Factory
class UnpinWidget internal constructor(
  private val repository: PinnedWidgetRepository
) {

  suspend operator fun invoke(glanceId: String) {
    repository.unpin(glanceId)
  }
}
