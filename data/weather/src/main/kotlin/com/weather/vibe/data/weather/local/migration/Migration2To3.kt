package com.weather.vibe.data.weather.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

private const val FROM_VERSION = 2
private const val TO_VERSION = 3
private const val ADD_OUTFIT_SUGGESTION_COLUMN =
  "ALTER TABLE ai_suggestion ADD COLUMN outfitSuggestion TEXT NOT NULL DEFAULT ''"

internal object Migration2To3 : Migration(FROM_VERSION, TO_VERSION) {

  override fun migrate(db: SupportSQLiteDatabase) {
    db.execSQL(ADD_OUTFIT_SUGGESTION_COLUMN)
  }
}
