package com.weather.vibe.feature.profile.ui.screen

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.weather.vibe.feature.profile.R

internal fun launchContactIntent(context: Context) {

  val email = context.getString(R.string.profile_contact_email)
  val subject = context.getString(R.string.profile_contact_subject)
  val mailtoUri = "mailto:$email?subject=${Uri.encode(subject)}".toUri()

  val intent = Intent(Intent.ACTION_SENDTO, mailtoUri).apply {
    putExtra(Intent.EXTRA_SUBJECT, subject)
  }

  startSafely(context = context, intent = intent)
}

internal fun launchPrivacyIntent(context: Context) {

  val url = context.getString(R.string.profile_privacy_url)
  val intent = Intent(Intent.ACTION_VIEW, url.toUri())

  startSafely(context = context, intent = intent)
}

private fun startSafely(context: Context, intent: Intent) {
  try {
    context.startActivity(intent)
  } catch (error: ActivityNotFoundException) {
    Log.w(TAG, "No activity available for intent: $intent", error)
  }
}

private const val TAG = "ProfileExternalLaunchers"
