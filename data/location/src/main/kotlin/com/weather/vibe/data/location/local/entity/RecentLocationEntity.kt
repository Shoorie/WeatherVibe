package com.weather.vibe.data.location.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_locations")
data class RecentLocationEntity(
  @PrimaryKey val id: Long,
  val admin1: String?,
  val country: String,
  val latitude: Double,
  val longitude: Double,
  val name: String,
  val timestamp: Long
)
