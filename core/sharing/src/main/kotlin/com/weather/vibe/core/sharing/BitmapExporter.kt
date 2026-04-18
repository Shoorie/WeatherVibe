package com.weather.vibe.core.sharing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.PNG
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import java.io.File
import java.io.FileOutputStream
import java.lang.System.currentTimeMillis

@Factory
class BitmapExporter(private val context: Context) {

  suspend fun exportPng(bitmap: Bitmap): Uri =
    withContext(Dispatchers.IO) {
      val sharedDir = ensureSharedDir()
      val outputFile = File(sharedDir, buildFilename())
      writeBitmap(bitmap, outputFile)
      getUriForFile(context, authority(), outputFile)
    }

  private fun ensureSharedDir(): File {
    val dir = File(context.cacheDir, SHARED_DIR_NAME)
    if (!dir.exists()) dir.mkdirs()
    return dir
  }

  private fun writeBitmap(bitmap: Bitmap, file: File) {
    FileOutputStream(file).use { stream ->
      bitmap.compress(PNG, PNG_QUALITY, stream)
      stream.flush()
    }
  }

  private fun buildFilename(): String =
    "${FILENAME_PREFIX}_${currentTimeMillis()}$FILENAME_EXTENSION"

  private fun authority(): String =
    "${context.packageName}$FILE_PROVIDER_SUFFIX"

  private companion object {
    const val SHARED_DIR_NAME = "shared"
    const val PNG_QUALITY = 100
    const val FILE_PROVIDER_SUFFIX = ".fileprovider"
    const val FILENAME_PREFIX = "weathervibe_brief"
    const val FILENAME_EXTENSION = ".png"
  }
}
