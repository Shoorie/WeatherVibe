package com.weather.vibe.core.designsystem.components.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.weather.vibe.core.designsystem.components.text.TypedTextDefaults.CARET
import com.weather.vibe.core.designsystem.components.text.TypedTextDefaults.TYPE_INTERVAL_MILLIS
import kotlinx.coroutines.delay

data class TypedText(
  val isComplete: Boolean,
  val visibleText: String
)

@Composable
fun rememberTypedText(text: String, key: Any): TypedText {
  var revealedCount by remember(key) { mutableIntStateOf(0) }
  LaunchedEffect(key, text) {
    revealedCount = 0
    while (revealedCount < text.length) {
      delay(TYPE_INTERVAL_MILLIS)
      revealedCount++
    }
  }
  val visibleCount = revealedCount.coerceAtMost(text.length)
  return TypedText(
    isComplete = visibleCount == text.length,
    visibleText = text.take(visibleCount)
  )
}

fun TypedText.withCaret(caretColor: Color): AnnotatedString =
  buildAnnotatedString {
    append(visibleText)
    if (!isComplete) {
      withStyle(SpanStyle(color = caretColor, fontWeight = FontWeight.Bold)) {
        append(CARET)
      }
    }
  }
