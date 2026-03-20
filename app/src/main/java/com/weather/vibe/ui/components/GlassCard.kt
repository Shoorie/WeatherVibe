package com.weather.vibe.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import com.weather.vibe.ui.theme.AppDimens

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    val borderColor = MaterialTheme.colorScheme.outline
    val shape = RoundedCornerShape(AppDimens.CardCornerRadius)

    Column(
        modifier = modifier
            .clip(shape)
            .drawBehind { drawRect(surfaceColor) }
            .border(AppDimens.BorderThickness, borderColor, shape)
            .padding(AppDimens.PaddingMedium),
        content = content
    )
}

@Composable
fun GlassCardSmall(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    content: @Composable ColumnScope.() -> Unit
) {
    val borderColor = MaterialTheme.colorScheme.outline
    val shape = RoundedCornerShape(AppDimens.CardCornerRadiusSmall)

    Column(
        modifier = modifier
            .clip(shape)
            .drawBehind { drawRect(backgroundColor) }
            .border(AppDimens.BorderThickness, borderColor, shape)
            .padding(AppDimens.PaddingSmall),
        content = content
    )
}
