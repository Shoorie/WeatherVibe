package com.weather.vibe.core.designsystem.components.header

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush

@Composable
fun VibeScreenScaffold(
  modifier: Modifier = Modifier,
  scrollBehavior: VibeScreenScrollBehavior? = null,
  header: @Composable () -> Unit,
  content: @Composable ColumnScope.() -> Unit
) {
  val rootModifier = modifier
    .fillMaxSize()
    .background(rememberAppBackgroundBrush())
    .then(
      if (scrollBehavior != null) {
        Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
      } else {
        Modifier
      }
    )

  Box(modifier = rootModifier) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
    ) {
      if (scrollBehavior != null) {
        AnimatedVisibility(
          visible = scrollBehavior.isHeaderVisible,
          enter = slideInVertically { fullHeight -> -fullHeight } + fadeIn(),
          exit = slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
        ) {
          header()
        }
      } else {
        header()
      }
      content()
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeScreenScaffold(
      header = {
        VibeScreenHeader(
          title = "Twój vibe",
          subtitle = "Jak oceniałeś ostatnie dni",
          onBackClicked = {},
          backContentDescription = "Wstecz"
        )
      },
      content = {}
    )
  }
}
