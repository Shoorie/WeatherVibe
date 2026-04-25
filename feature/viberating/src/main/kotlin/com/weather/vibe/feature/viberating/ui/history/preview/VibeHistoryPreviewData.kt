package com.weather.vibe.feature.viberating.ui.history.preview

import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import java.time.YearMonth

internal object VibeHistoryPreviewData {
  private const val PreviewYear: Int = 2026
  private const val PreviewMonth: Int = 4

  val emptyState: VibeHistoryUiState =
    VibeHistoryUiState.emptyFor(YearMonth.of(PreviewYear, PreviewMonth))
}
