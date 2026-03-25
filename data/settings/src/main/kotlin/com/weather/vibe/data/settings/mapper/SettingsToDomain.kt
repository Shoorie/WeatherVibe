package com.weather.vibe.data.settings.mapper

import com.weather.vibe.data.settings.local.entity.SettingsEntity
import com.weather.vibe.data.settings.remote.dto.SettingsResponse
import com.weather.vibe.domain.settings.model.SettingsItem

internal fun SettingsResponse.toDomain(): SettingsItem =
  SettingsItem(
    id = id,
    title = title
  )

internal fun SettingsEntity.toDomain(): SettingsItem =
  SettingsItem(
    id = id,
    title = title
  )

internal fun SettingsItem.toEntity(): SettingsEntity =
  SettingsEntity(
    id = id,
    title = title
  )

