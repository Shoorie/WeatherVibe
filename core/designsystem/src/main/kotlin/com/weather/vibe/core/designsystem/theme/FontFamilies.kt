package com.weather.vibe.core.designsystem.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation.Settings
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import com.weather.vibe.core.designsystem.R

private const val WEIGHT_LIGHT = 300
private const val WEIGHT_NORMAL = 400
private const val WEIGHT_MEDIUM = 500
private const val WEIGHT_SEMI_BOLD = 600
private const val WEIGHT_BOLD = 700

@OptIn(ExperimentalTextApi::class)
private fun font(weight: Int, compose: FontWeight): Font =
  Font(
    resId = R.font.manrope_variable,
    weight = compose,
    variationSettings = Settings(weight(weight))
  )

internal val ManropeFontFamily: FontFamily = FontFamily(
  font(WEIGHT_LIGHT, Light),
  font(WEIGHT_NORMAL, Normal),
  font(WEIGHT_MEDIUM, Medium),
  font(WEIGHT_SEMI_BOLD, SemiBold),
  font(WEIGHT_BOLD, Bold)
)
