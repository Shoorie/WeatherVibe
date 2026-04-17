package com.weather.vibe.core.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class SuspendRunCatchingTest {

  @Test
  fun `when block succeeds, then success carries value`() = runTest {

    val result = suspendRunCatching { VALUE }

    expectThat(result.getOrNull()).isEqualTo(VALUE)
  }

  @Test
  fun `when block throws, then failure carries exception`() = runTest {

    val cause = IllegalStateException("offline")

    val result = suspendRunCatching<Int> { throw cause }

    expectThat(result.exceptionOrNull()).isA<IllegalStateException>().isEqualTo(cause)
  }

  @Test
  fun `given cancellation raised, when block throws, then cancellation propagates`() = runTest {

    expectThrows<CancellationException> {
      suspendRunCatching<Int> { throw CancellationException("cancelled") }
    }
  }

  private companion object {
    const val VALUE = 42
  }
}
