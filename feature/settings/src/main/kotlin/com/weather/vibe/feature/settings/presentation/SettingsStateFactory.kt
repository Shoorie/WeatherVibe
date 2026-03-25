package com.weather.vibe.feature.settings.presentation

import com.weather.vibe.domain.settings.model.SettingsItem
import com.weather.vibe.feature.settings.presentation.state.SettingsItemUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import org.koin.core.annotation.Factory

@Factory
internal class SettingsStateFactory {

  fun create(items: List<SettingsItem>): SettingsUiState.Loaded =
    SettingsUiState.Loaded(items = items.map(::createItem))

  private fun createItem(item: SettingsItem): SettingsItemUiState =
    SettingsItemUiState(
      id = item.id,
      title = item.title
    )
}

