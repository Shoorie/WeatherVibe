package com.weather.vibe.core.designsystem.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun <T> rememberOnResumeValue(produce: () -> T): State<T> {

  val lifecycle = LocalLifecycleOwner.current.lifecycle
  val state = remember { mutableStateOf(produce()) }

  DisposableEffect(lifecycle) {
    val observer = LifecycleEventObserver { _, event ->
      if (event == ON_RESUME) {
        state.value = produce()
      }
    }
    lifecycle.addObserver(observer)
    onDispose { lifecycle.removeObserver(observer) }
  }

  return state
}
