package com.weather.vibe.feature.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.GlassBorder
import com.weather.vibe.ui.theme.GlassSurfaceHeavy
import com.weather.vibe.ui.theme.TextPrimary
import com.weather.vibe.ui.theme.TextSecondary
import com.weather.vibe.ui.theme.TextTertiary
import com.weather.vibe.ui.theme.WeatherVibeTheme
import com.weather.vibe.ui.theme.AccentSkyBlue

@Composable
fun LocationSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val shape = RoundedCornerShape(AppDimens.CardCornerRadius)
    val surfaceColor = GlassSurfaceHeavy

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .drawBehind { drawRect(surfaceColor) }
            .border(AppDimens.BorderThickness, GlassBorder, shape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.padding(
                start = AppDimens.PaddingSmall,
                end = AppDimens.PaddingExtraSmall
            )
        )
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = AppDimens.PaddingMedium)
                .focusRequester(focusRequester),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
            cursorBrush = SolidColor(AccentSkyBlue),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {}),
            decorationBox = { innerTextField ->
                Box {
                    if (query.isEmpty()) {
                        Text(
                            text = "Szukaj miasta...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextTertiary
                        )
                    }
                    innerTextField()
                }
            }
        )
        IconButton(onClick = onDismiss) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Zamknij wyszukiwanie",
                tint = TextSecondary
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
private fun LocationSearchBarPreview() {
    WeatherVibeTheme {
        LocationSearchBar(
            query = "Warsz",
            onQueryChange = {},
            onDismiss = {}
        )
    }
}
