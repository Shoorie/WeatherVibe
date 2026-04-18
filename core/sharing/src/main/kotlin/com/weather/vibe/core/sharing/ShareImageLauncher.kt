package com.weather.vibe.core.sharing

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_STREAM
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.Intent.createChooser
import android.net.Uri
import org.koin.core.annotation.Factory

@Factory
class ShareImageLauncher(private val context: Context) {

  fun launch(imageUri: Uri, chooserTitle: String) {

    val sendIntent = Intent(ACTION_SEND).apply {
      type = MIME_TYPE_PNG
      putExtra(EXTRA_STREAM, imageUri)
      addFlags(FLAG_GRANT_READ_URI_PERMISSION)
    }

    val chooser = createChooser(sendIntent, chooserTitle)
      .apply { addFlags(FLAG_ACTIVITY_NEW_TASK) }
    context.startActivity(chooser)
  }

  private companion object {
    const val MIME_TYPE_PNG = "image/png"
  }
}
