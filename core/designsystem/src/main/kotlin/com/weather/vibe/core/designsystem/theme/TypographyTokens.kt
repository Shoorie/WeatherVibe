package com.weather.vibe.core.designsystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal object FontWeightTokens {
  val Light = FontWeight.Light
  val Medium = FontWeight.Medium
  val Normal = FontWeight.Normal
  val SemiBold = FontWeight.SemiBold
}

internal object FontSizeTokens {
  val BodyLarge = 16.sp
  val BodyMedium = 14.sp
  val BodySmall = 12.sp
  val DisplayLarge = 72.sp
  val DisplayMedium = 48.sp
  val DisplaySmall = 36.sp
  val HeadlineLarge = 28.sp
  val HeadlineMedium = 24.sp
  val LabelMedium = 12.sp
  val LabelSmall = 10.sp
  val TitleLarge = 18.sp
  val TitleMedium = 16.sp
  val TitleSmall = 14.sp
}

internal object LineHeightTokens {
  val BodyLarge = 24.sp
  val BodyMedium = 20.sp
  val BodySmall = 16.sp
  val DisplayLarge = 80.sp
  val DisplayMedium = 56.sp
  val DisplaySmall = 44.sp
  val HeadlineLarge = 36.sp
  val HeadlineMedium = 32.sp
  val Label = 16.sp
  val TitleLarge = 28.sp
  val TitleMedium = 24.sp
  val TitleSmall = 20.sp
}

internal object LetterSpacingTokens {
  val BodyLarge = 0.5.sp
  val BodyMedium = 0.25.sp
  val BodySmall = 0.4.sp
  val Default = 0.sp
  val DisplayLarge = (-1).sp
  val Label = 0.5.sp
  val TitleMedium = 0.15.sp
  val TitleSmall = 0.1.sp
}

internal object TypographyTokens {

  val BodyLarge = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Normal,
    fontSize = FontSizeTokens.BodyLarge,
    lineHeight = LineHeightTokens.BodyLarge,
    letterSpacing = LetterSpacingTokens.BodyLarge
  )

  val BodyMedium = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Normal,
    fontSize = FontSizeTokens.BodyMedium,
    lineHeight = LineHeightTokens.BodyMedium,
    letterSpacing = LetterSpacingTokens.BodyMedium
  )

  val BodySmall = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Normal,
    fontSize = FontSizeTokens.BodySmall,
    lineHeight = LineHeightTokens.BodySmall,
    letterSpacing = LetterSpacingTokens.BodySmall
  )

  val DisplayLarge = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Light,
    fontSize = FontSizeTokens.DisplayLarge,
    lineHeight = LineHeightTokens.DisplayLarge,
    letterSpacing = LetterSpacingTokens.DisplayLarge
  )

  val DisplayMedium = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Light,
    fontSize = FontSizeTokens.DisplayMedium,
    lineHeight = LineHeightTokens.DisplayMedium,
    letterSpacing = LetterSpacingTokens.Default
  )

  val DisplaySmall = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Normal,
    fontSize = FontSizeTokens.DisplaySmall,
    lineHeight = LineHeightTokens.DisplaySmall,
    letterSpacing = LetterSpacingTokens.Default
  )

  val HeadlineLarge = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.SemiBold,
    fontSize = FontSizeTokens.HeadlineLarge,
    lineHeight = LineHeightTokens.HeadlineLarge,
    letterSpacing = LetterSpacingTokens.Default
  )

  val HeadlineMedium = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Normal,
    fontSize = FontSizeTokens.HeadlineMedium,
    lineHeight = LineHeightTokens.HeadlineMedium,
    letterSpacing = LetterSpacingTokens.Default
  )

  val LabelMedium = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Medium,
    fontSize = FontSizeTokens.LabelMedium,
    lineHeight = LineHeightTokens.Label,
    letterSpacing = LetterSpacingTokens.Label
  )

  val LabelSmall = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Medium,
    fontSize = FontSizeTokens.LabelSmall,
    lineHeight = LineHeightTokens.Label,
    letterSpacing = LetterSpacingTokens.Label
  )

  val TitleLarge = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.SemiBold,
    fontSize = FontSizeTokens.TitleLarge,
    lineHeight = LineHeightTokens.TitleLarge,
    letterSpacing = LetterSpacingTokens.Default
  )

  val TitleMedium = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Medium,
    fontSize = FontSizeTokens.TitleMedium,
    lineHeight = LineHeightTokens.TitleMedium,
    letterSpacing = LetterSpacingTokens.TitleMedium
  )

  val TitleSmall = TextStyle(
    fontFamily = ManropeFontFamily,
    fontWeight = FontWeightTokens.Medium,
    fontSize = FontSizeTokens.TitleSmall,
    lineHeight = LineHeightTokens.TitleSmall,
    letterSpacing = LetterSpacingTokens.TitleSmall
  )
}
