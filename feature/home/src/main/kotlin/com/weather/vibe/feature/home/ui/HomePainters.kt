package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.weather.vibe.feature.home.R

internal object HomePainters {

  @Composable
  fun spotifyIcon(): Painter =
    painterResource(id = R.drawable.ic_spotify)

  @Composable
  fun ytMusicIcon(): Painter =
    painterResource(id = R.drawable.ic_yt_music)
}
