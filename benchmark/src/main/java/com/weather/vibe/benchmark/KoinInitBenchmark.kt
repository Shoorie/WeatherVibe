package com.weather.vibe.benchmark

import androidx.benchmark.macro.CompilationMode.Partial
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.StartupMode.COLD
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.weather.vibe.core.tracing.TraceSections.KOIN_INITIALIZATION
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Measures how long Koin dependency injection takes to initialize
 * during cold startup.
 *
 * The Application class wraps `startKoin { ... }` in a
 * `Trace.beginSection(...)` block, and this benchmark reads back that
 * trace section from the Perfetto recording of a cold start. The result
 * is the time spent wiring up the DI graph — everything else in startup
 * is excluded.
 *
 * Use this as an early warning: if the number creeps up as you add more
 * modules, it's a signal to push expensive singletons to lazy initialization
 * or off the main thread.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class KoinInitBenchmark {

  @get:Rule
  val rule = MacrobenchmarkRule()

  @Test
  @OptIn(ExperimentalMetricApi::class)
  fun koinInitialization() {
    rule.measureRepeated(
      packageName = PACKAGE_NAME,
      metrics = listOf(TraceSectionMetric(KOIN_INITIALIZATION)),
      compilationMode = Partial(),
      iterations = 5,
      startupMode = COLD,
      setupBlock = { pressHome() },
      measureBlock = { startActivityAndWait() }
    )
  }
}
