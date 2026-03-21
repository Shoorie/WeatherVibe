package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.BorderThickness
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.preview.SearchBarQueryPreviewParameterProvider
import com.weather.vibe.feature.home.ui.HomeResources.Texts.closeSearchContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.searchHint

@Composable
internal fun LocationSearchBar(
  modifier: Modifier = Modifier,
  query: String,
  onQueryChange: (String) -> Unit,
  onDismiss: () -> Unit
) {
  val focusRequester = remember { FocusRequester() }
  val surfaceColor = colors.glassSurfaceHeavy

  LaunchedEffect(Unit) {
    focusRequester.requestFocus()
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .drawBehind { drawRect(surfaceColor) }
      .border(BorderThickness, colors.glassBorder, shapes.card),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = Icons.Default.Search,
      contentDescription = null,
      tint = colors.onSurfaceVariant,
      modifier = Modifier.padding(
        start = PaddingSmall,
        end = PaddingExtraSmall
      )
    )
    BasicTextField(
      value = query,
      onValueChange = onQueryChange,
      modifier = Modifier
        .weight(1f)
        .padding(vertical = PaddingMedium)
        .focusRequester(focusRequester),
      textStyle = typography.bodyLarge
        .copy(color = colors.onBackground),
      cursorBrush = SolidColor(colors.accent),
      singleLine = true,
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
      keyboardActions = KeyboardActions(onSearch = {}),
      decorationBox = { innerTextField ->
        Box {
          if (query.isEmpty()) {
            Text(
              text = searchHint(),
              style = typography.bodyLarge,
              color = colors.textTertiary
            )
          }
          innerTextField()
        }
      }
    )
    IconButton(onClick = onDismiss) {
      Icon(
        imageVector = Icons.Default.Close,
        contentDescription = closeSearchContentDescription(),
        tint = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SearchBarQueryPreviewParameterProvider::class)
  query: String
) {
  WeatherVibeTheme {
    LocationSearchBar(
      query = query,
      onQueryChange = {},
      onDismiss = {}
    )
  }
}
