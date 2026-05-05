package com.weather.vibe.data.weather.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

private const val FROM_VERSION = 3
private const val TO_VERSION = 4
private const val DROP_AI_SUGGESTION = "DROP TABLE IF EXISTS ai_suggestion"
private const val RECREATE_AI_SUGGESTION = """
  CREATE TABLE ai_suggestion (
    briefText TEXT NOT NULL,
    fetchedAt INTEGER NOT NULL,
    genresCsv TEXT NOT NULL,
    mood TEXT NOT NULL,
    moodDescription TEXT NOT NULL,
    outfitSuggestion TEXT NOT NULL,
    simplifiedCondition TEXT NOT NULL,
    temperatureRange TEXT NOT NULL,
    timeOfDay TEXT NOT NULL,
    tone TEXT NOT NULL,
    weatherKeyHash TEXT NOT NULL,
    PRIMARY KEY(weatherKeyHash, tone)
  )
"""

internal object Migration3To4 : Migration(FROM_VERSION, TO_VERSION) {

  override fun migrate(db: SupportSQLiteDatabase) {
    db.execSQL(DROP_AI_SUGGESTION)
    db.execSQL(RECREATE_AI_SUGGESTION)
  }
}
