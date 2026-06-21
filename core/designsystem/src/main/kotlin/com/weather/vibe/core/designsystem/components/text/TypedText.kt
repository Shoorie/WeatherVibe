package com.weather.vibe.core.designsystem.components.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.delay

data class TypedText(
  val isDone: Boolean,
  val shown: String
)

@Composable
fun rememberTypedText(text: String, key: Any): TypedText {
  var shown by remember(key) { mutableStateOf("") }
  LaunchedEffect(key, text) {
    shown = ""
    for (index in 1..text.length) {
      shown = text.substring(0, index)
      delay(TYPE_INTERVAL_MILLIS)
    }
  }
  return TypedText(isDone = shown.length == text.length, shown = shown)
}

fun TypedText.withCaret(caretColor: Color): AnnotatedString =
  buildAnnotatedString {
    append(shown)
    if (!isDone) {
      withStyle(SpanStyle(color = caretColor, fontWeight = FontWeight.Bold)) {
        append(CARET)
      }
    }
  }

private const val TYPE_INTERVAL_MILLIS = 18L
private const val CARET = "▏"
