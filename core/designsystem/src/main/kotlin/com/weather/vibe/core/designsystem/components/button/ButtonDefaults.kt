package com.weather.vibe.core.designsystem.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small

internal object ButtonDefaults {

  val MinHeight = 52.dp
  val BrandContent = Color.White
  val BrandContentPadding = PaddingValues(horizontal = Large, vertical = Small)

  @Composable
  fun brandButtonColors(containerColor: Color) =
    ButtonDefaults.buttonColors(
      containerColor = containerColor,
      contentColor = BrandContent
    )
}
