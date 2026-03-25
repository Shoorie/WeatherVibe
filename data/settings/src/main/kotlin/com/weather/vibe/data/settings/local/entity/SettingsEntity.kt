package com.weather.vibe.data.settings.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settingss")
data class SettingsEntity(
  @PrimaryKey @ColumnInfo(name = "id") val id: String,
  @ColumnInfo(name = "title") val title: String
)

