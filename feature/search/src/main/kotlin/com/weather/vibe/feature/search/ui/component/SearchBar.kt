package com.weather.vibe.feature.search.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.preview.SearchBarPreview
import com.weather.vibe.feature.search.ui.SearchResources.Texts.searchHint

@Composable
internal fun SearchBar(
  modifier: Modifier = Modifier,
  query: String,
  onQueryChange: (String) -> Unit,
  onBack: () -> Unit
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
      .border(Stroke.Border, colors.glassBorder, shapes.card),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(onClick = onBack) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = null,
        tint = colors.onSurfaceVariant
      )
    }
    BasicTextField(
      value = query,
      onValueChange = onQueryChange,
      modifier = Modifier
        .weight(1f)
        .padding(vertical = Padding.Medium)
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
    Icon(
      imageVector = Icons.Default.Search,
      contentDescription = null,
      tint = colors.onSurfaceVariant,
      modifier = Modifier.padding(
        start = Padding.ExtraSmall,
        end = Padding.Small
      )
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SearchBarPreview::class)
  query: String
) {
  WeatherVibeTheme {
    SearchBar(
      query = query,
      onQueryChange = {},
      onBack = {}
    )
  }
}
