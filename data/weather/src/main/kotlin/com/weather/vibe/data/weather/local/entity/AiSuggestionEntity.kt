package com.weather.vibe.data.weather.local.entity

import androidx.room.Entity

@Entity(
  tableName = "ai_suggestion",
  primaryKeys = ["weatherKeyHash", "tone"]
)
data class AiSuggestionEntity(
  val briefText: String,
  val fetchedAt: Long,
  val genresCsv: String,
  val mood: String,
  val moodDescription: String,
  val simplifiedCondition: String,
  val temperatureRange: String,
  val timeOfDay: String,
  val tone: String,
  val weatherKeyHash: String
)
