package com.weather.vibe.feature.profile.ui.component.editsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.VibePrimaryButton
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.editSheetBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.editSheetFieldLabel
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.editSheetSave
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.editSheetTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditProfileSheet(
  modifier: Modifier = Modifier,
  state: ProfileEditSheetUiState,
  onDismiss: () -> Unit,
  onUsernameChange: (String) -> Unit,
  onSubmit: () -> Unit,
  sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
  ModalBottomSheet(
    modifier = modifier,
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = colors.sheetSurface
  ) {
    EditProfileSheetContent(
      username = state.username,
      canSave = state.canSave,
      onUsernameChange = onUsernameChange,
      onSubmit = onSubmit
    )
  }
}

@Composable
private fun EditProfileSheetContent(
  username: String,
  canSave: Boolean,
  onUsernameChange: (String) -> Unit,
  onSubmit: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = Medium)
      .padding(bottom = Large),
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    SheetHeader()
    UsernameField(
      value = username,
      onValueChange = onUsernameChange,
      onDone = onSubmit
    )
    VibePrimaryButton(
      text = editSheetSave(),
      enabled = canSave,
      onClick = onSubmit
    )
  }
}

@Composable
private fun SheetHeader() {
  Column(verticalArrangement = Arrangement.spacedBy(Small)) {
    Text(
      text = editSheetTitle(),
      style = typography.titleMedium.copy(fontWeight = SemiBold),
      color = colors.onBackground
    )
    Text(
      text = editSheetBody(),
      style = typography.bodySmall,
      color = colors.onSurfaceVariant
    )
  }
}

@Composable
private fun UsernameField(
  value: String,
  onValueChange: (String) -> Unit,
  onDone: () -> Unit
) {
  OutlinedTextField(
    modifier = Modifier.fillMaxWidth(),
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = editSheetFieldLabel()) },
    singleLine = true,
    shape = shapes.card,
    keyboardOptions = KeyboardOptions(
      capitalization = KeyboardCapitalization.Words,
      imeAction = ImeAction.Done
    ),
    keyboardActions = KeyboardActions(onDone = { onDone() })
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    EditProfileSheet(
      state = ProfileEditSheetUiState(
        isVisible = true,
        username = "John",
        canSave = true
      ),
      onDismiss = {},
      onUsernameChange = {},
      onSubmit = {}
    )
  }
}
