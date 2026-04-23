package com.weather.vibe.data.location.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object Migration2To3 : Migration(startVersion = 2, endVersion = 3) {

  override fun migrate(db: SupportSQLiteDatabase) {
    db.execSQL(PURGE_ORPHANS)
    db.execSQL(CREATE_SNAPSHOT_WITH_FK)
    db.execSQL(COPY_SNAPSHOTS)
    db.execSQL(DROP_OLD_SNAPSHOT)
    db.execSQL(RENAME_SNAPSHOT)
    db.execSQL(CREATE_SNAPSHOT_FK_INDEX)
  }

  private const val PURGE_ORPHANS = """
    DELETE FROM location_weather_snapshot
    WHERE locationId NOT IN (SELECT locationId FROM favorite_locations)
  """

  private const val CREATE_SNAPSHOT_WITH_FK = """
    CREATE TABLE location_weather_snapshot_new (
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
      windKph REAL NOT NULL,
      FOREIGN KEY(locationId) REFERENCES favorite_locations(locationId) ON DELETE CASCADE
    )
  """

  private const val COPY_SNAPSHOTS = """
    INSERT INTO location_weather_snapshot_new
    SELECT * FROM location_weather_snapshot
  """

  private const val DROP_OLD_SNAPSHOT = "DROP TABLE location_weather_snapshot"

  private const val RENAME_SNAPSHOT =
    "ALTER TABLE location_weather_snapshot_new RENAME TO location_weather_snapshot"

  private const val CREATE_SNAPSHOT_FK_INDEX = """
    CREATE INDEX IF NOT EXISTS index_location_weather_snapshot_locationId
      ON location_weather_snapshot(locationId)
  """
}
