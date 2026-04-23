package com.weather.vibe.data.location.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object Migration1To2 : Migration(startVersion = 1, endVersion = 2) {

  override fun migrate(db: SupportSQLiteDatabase) {
    db.execSQL(CREATE_FAVORITES)
    db.execSQL(CREATE_FAVORITE_LOCATION_INDEX)
    db.execSQL(CREATE_WEATHER_SNAPSHOT)
  }

  private const val CREATE_FAVORITES = """
    CREATE TABLE IF NOT EXISTS favorite_locations (
      id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
      admin1 TEXT,
      country TEXT NOT NULL,
      isDefault INTEGER NOT NULL,
      label TEXT,
      latitude REAL NOT NULL,
      locationId INTEGER NOT NULL,
      longitude REAL NOT NULL,
      name TEXT NOT NULL,
      position INTEGER NOT NULL
    )
  """

  private const val CREATE_FAVORITE_LOCATION_INDEX = """
    CREATE UNIQUE INDEX IF NOT EXISTS index_favorite_locations_locationId
      ON favorite_locations(locationId)
  """

  private const val CREATE_WEATHER_SNAPSHOT = """
    CREATE TABLE IF NOT EXISTS location_weather_snapshot (
      locationId INTEGER PRIMARY KEY NOT NULL,
      condition TEXT NOT NULL,
      feelsLikeC REAL NOT NULL,
      highC REAL NOT NULL,
      hourlyTemperaturesJson TEXT NOT NULL,
      humidityPercent INTEGER NOT NULL,
      isDay INTEGER NOT NULL,
      lowC REAL NOT NULL,
      precipitationChancePercent INTEGER NOT NULL,
      temperatureC REAL NOT NULL,
      updatedAtEpochMs INTEGER NOT NULL,
      windKph REAL NOT NULL
    )
  """
}
