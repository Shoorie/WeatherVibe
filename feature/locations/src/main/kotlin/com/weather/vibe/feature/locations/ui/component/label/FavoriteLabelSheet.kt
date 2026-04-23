package com.weather.vibe.feature.locations.ui.component.label

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.VibePrimaryButton
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelPresetFamily
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelPresetHome
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelPresetVacation
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelPresetWork
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelSheetPlaceholder
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelSheetSave
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelSheetSkip
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelSheetSubtitle
import com.weather.vibe.feature.locations.ui.component.row.labelPillContainerColor
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteLabelSheet(
  modifier: Modifier = Modifier,
  title: String,
  locationName: String,
  initialLabel: String?,
  onDismiss: () -> Unit,
  onSubmit: (String?) -> Unit,
  sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
  var labelText by rememberSaveable(locationName) { mutableStateOf(initialLabel.orEmpty()) }
  ModalBottomSheet(
    modifier = modifier,
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = colors.sheetSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = Medium)
        .padding(bottom = Large),
      verticalArrangement = Arrangement.spacedBy(Medium)
    ) {
      SheetHeader(
        title = title,
        locationName = locationName
      )
      LabelInput(
        value = labelText,
        onValueChange = { next -> labelText = next.take(LocationsDefaults.LabelMaxLength) }
      )
      LabelCharCounter(length = labelText.length)
      PresetChips(onPresetSelected = { labelText = it })
      SheetButtons(
        labelText = labelText,
        onSkip = { onSubmit(null) },
        onSave = { onSubmit(labelText.trimmedOrNull()) }
      )
    }
  }
}

@Composable
private fun SheetHeader(
  title: String,
  locationName: String
) {
  Column(verticalArrangement = Arrangement.spacedBy(ExtraSmall)) {
    Text(
      text = title,
      style = typography.titleLarge,
      color = colors.onBackground
    )
    Text(
      text = locationName,
      style = typography.titleSmall,
      color = colors.accent
    )
    Text(
      text = labelSheetSubtitle(),
      style = typography.bodySmall,
      color = colors.onSurfaceVariant
    )
  }
}

private fun String.trimmedOrNull(): String? =
  trim().takeIf(String::isNotEmpty)

@Composable
private fun LabelInput(
  value: String,
  onValueChange: (String) -> Unit
) {
  val focusRequester = remember { FocusRequester() }
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.glassSurface)
      .border(
        width = Border,
        color = colors.outlineVariant,
        shape = shapes.card
      )
      .clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { focusRequester.requestFocus() }
      )
      .padding(horizontal = Medium, vertical = Small),
    contentAlignment = Alignment.CenterStart
  ) {
    if (value.isEmpty()) {
      Text(
        text = labelSheetPlaceholder(),
        style = typography.bodyMedium,
        color = colors.textTertiary
      )
    }
    BasicTextField(
      modifier = Modifier
        .fillMaxWidth()
        .focusRequester(focusRequester),
      value = value,
      onValueChange = onValueChange,
      textStyle = typography.bodyMedium.copy(color = colors.onBackground),
      cursorBrush = SolidColor(colors.accent),
      singleLine = true
    )
  }
}

@Composable
private fun LabelCharCounter(length: Int) {
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentWidth(align = Alignment.End),
    text = "$length/${LocationsDefaults.LabelMaxLength}",
    style = typography.labelSmall,
    color = colors.textTertiary
  )
}

@Composable
private fun PresetChips(onPresetSelected: (String) -> Unit) {
  val scrollState = rememberScrollState()
  val home = labelPresetHome()
  val work = labelPresetWork()
  val vacation = labelPresetVacation()
  val family = labelPresetFamily()
  val presets = remember(home, work, vacation, family) {
    persistentListOf(home, work, vacation, family)
  }
  Row(
    modifier = Modifier.horizontalScroll(scrollState),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    presets.forEachIndexed { index, preset ->
      PresetChip(
        label = preset,
        containerColor = labelPillContainerColor(index = index),
        onClick = { onPresetSelected(preset) }
      )
    }
  }
}

@Composable
private fun PresetChip(
  label: String,
  containerColor: Color,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(shapes.pill)
      .background(containerColor)
      .clickable(onClick = onClick)
      .padding(horizontal = Medium, vertical = Small),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = label,
      style = typography.labelMedium,
      color = colors.onAccent
    )
  }
}

@Composable
private fun SheetButtons(
  labelText: String,
  onSkip: () -> Unit,
  onSave: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    VibePrimaryButton(
      modifier = Modifier.weight(1f),
      text = labelSheetSkip(),
      containerColor = colors.glassSurface,
      contentColor = colors.onBackground,
      onClick = onSkip
    )
    VibePrimaryButton(
      modifier = Modifier.weight(1f),
      text = labelSheetSave(),
      enabled = labelText.isNotBlank(),
      onClick = onSave
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    FavoriteLabelSheet(
      title = "Label this place",
      locationName = "Warszawa",
      initialLabel = null,
      onDismiss = {},
      onSubmit = {}
    )
  }
}
