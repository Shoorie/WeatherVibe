package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal class VibeSegmentedControlPreview :
  PreviewParameterProvider<ImmutableList<VibeSegment<String>>> {

  private val twoSegmentsFirstSelected: ImmutableList<VibeSegment<String>> =
    persistentListOf(
      VibeSegment(
        value = "c",
        label = "°C",
        contentDescription = "Celsius",
        isSelected = true
      ),
      VibeSegment(
        value = "f",
        label = "°F",
        contentDescription = "Fahrenheit",
        isSelected = false
      )
    )

  private val twoSegmentsSecondSelected: ImmutableList<VibeSegment<String>> =
    persistentListOf(
      VibeSegment(
        value = "c",
        label = "°C",
        contentDescription = "Celsius",
        isSelected = false
      ),
      VibeSegment(
        value = "f",
        label = "°F",
        contentDescription = "Fahrenheit",
        isSelected = true
      )
    )

  private val threeSegmentsMiddleSelected: ImmutableList<VibeSegment<String>> =
    persistentListOf(
      VibeSegment(
        value = "run",
        label = "🏃 Running",
        contentDescription = "Running",
        isSelected = false
      ),
      VibeSegment(
        value = "walk",
        label = "🚶 Walking",
        contentDescription = "Walking",
        isSelected = true
      ),
      VibeSegment(
        value = "bike",
        label = "🚴 Cycling",
        contentDescription = "Cycling",
        isSelected = false
      )
    )

  override val values: Sequence<ImmutableList<VibeSegment<String>>> =
    sequenceOf(
      twoSegmentsFirstSelected,
      twoSegmentsSecondSelected,
      threeSegmentsMiddleSelected
    )
}
