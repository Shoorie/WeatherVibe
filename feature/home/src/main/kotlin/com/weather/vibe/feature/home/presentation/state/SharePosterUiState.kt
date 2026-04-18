package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey

@Immutable
internal data class SharePosterUiState(
  val cityName: String,
  val conditionEmoji: String,
  val conditionLabel: String,
  val dateLabel: String,
  val gradientKey: ShareGradientKey,
  val outfit: String?,
  val quoteText: String,
  val temperature: String,
  val wordmarkHeadline: String
)
