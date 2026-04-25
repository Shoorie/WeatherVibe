package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import java.time.YearMonth

internal class VibeHistoryScreenPreview : PreviewParameterProvider<VibeHistoryUiState> {

  private val emptyState: VibeHistoryUiState =
    VibeHistoryUiState.emptyFor(YearMonth.of(PREVIEW_YEAR, PREVIEW_MONTH))

  override val values: Sequence<VibeHistoryUiState> =
    sequenceOf(emptyState)

  private companion object {
    const val PREVIEW_YEAR: Int = 2026
    const val PREVIEW_MONTH: Int = 4
  }
}
