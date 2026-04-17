package com.weather.vibe.core.coroutines

import kotlinx.coroutines.CancellationException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

inline fun <T> suspendRunCatching(block: () -> T): Result<T> =
  try {
    success(block())
  } catch (cancellation: CancellationException) {
    throw cancellation
  } catch (throwable: Throwable) {
    failure(throwable)
  }
