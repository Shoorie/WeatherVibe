package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.weather.model.UserDispositionEntry

internal fun List<RatingEntry>.toDispositionEntries(): List<UserDispositionEntry> =
  map { entry ->
    UserDispositionEntry(
      note = entry.note,
      rating = entry.rating,
      recordedAtEpochMillis = entry.createdAtEpochMs
    )
  }
