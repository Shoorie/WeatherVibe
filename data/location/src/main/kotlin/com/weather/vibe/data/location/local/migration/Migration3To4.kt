package com.weather.vibe.data.location.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object Migration3To4 : Migration(startVersion = 3, endVersion = 4) {

  override fun migrate(db: SupportSQLiteDatabase) {
    db.execSQL(CREATE_SNAPSHOT_FK_INDEX)
  }

  private const val CREATE_SNAPSHOT_FK_INDEX = """
    CREATE INDEX IF NOT EXISTS index_location_weather_snapshot_locationId
      ON location_weather_snapshot(locationId)
  """
}
