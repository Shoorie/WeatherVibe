package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceOptionUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceRowUiState
import kotlinx.collections.immutable.persistentListOf

internal class AppearanceRowPreviewProvider :
  PreviewParameterProvider<ProfileAppearanceRowUiState> {

  private val autoSelected: ProfileAppearanceRowUiState =
    stateFor(current = AUTO)

  private val lightSelected: ProfileAppearanceRowUiState =
    stateFor(current = LIGHT)

  private val darkSelected: ProfileAppearanceRowUiState =
    stateFor(current = DARK)

  override val values: Sequence<ProfileAppearanceRowUiState> =
    sequenceOf(autoSelected, lightSelected, darkSelected)

  private fun stateFor(current: ThemeMode): ProfileAppearanceRowUiState =
    ProfileAppearanceRowUiState(
      body = "Light, dark or follow system",
      current = current,
      options = persistentListOf(
        option(current = current, mode = LIGHT, label = "Light"),
        option(current = current, mode = AUTO, label = "Auto"),
        option(current = current, mode = DARK, label = "Dark")
      ),
      title = "Appearance"
    )

  private fun option(
    current: ThemeMode,
    mode: ThemeMode,
    label: String
  ): ProfileAppearanceOptionUiState =
    ProfileAppearanceOptionUiState(
      isSelected = current == mode,
      label = label,
      mode = mode
    )
}
