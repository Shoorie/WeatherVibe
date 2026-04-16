package com.weather.vibe.notifications.ui

import android.content.Context
import com.weather.vibe.notifications.R
import org.koin.core.annotation.Factory

@Factory
internal class MorningBriefResources(private val context: Context) {

  fun title(): String =
    context.getString(R.string.alerts_morning_brief_title)
}
