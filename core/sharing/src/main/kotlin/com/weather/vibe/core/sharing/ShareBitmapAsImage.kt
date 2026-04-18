package com.weather.vibe.core.sharing

import android.graphics.Bitmap
import org.koin.core.annotation.Factory

@Factory
class ShareBitmapAsImage(
  private val exporter: BitmapExporter,
  private val launcher: ShareImageLauncher
) {

  suspend operator fun invoke(bitmap: Bitmap, chooserTitle: String) {
    val uri = exporter.exportPng(bitmap)
    launcher.launch(imageUri = uri, chooserTitle = chooserTitle)
  }
}
