package com.weather.vibe.data.location.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "favorite_locations",
  indices = [Index(value = ["locationId"], unique = true)]
)
data class FavoriteEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val admin1: String?,
  val country: String,
  val isDefault: Boolean,
  val label: String?,
  val latitude: Double,
  val locationId: Long,
  val longitude: Double,
  val name: String,
  val position: Int
)
