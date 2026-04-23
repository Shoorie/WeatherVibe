package com.weather.vibe.core.androidext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showShortToast(
  @StringRes messageRes: Int,
  vararg formatArgs: Any
) {
  Toast.makeText(
    this,
    getString(messageRes, *formatArgs),
    Toast.LENGTH_SHORT
  ).show()
}
