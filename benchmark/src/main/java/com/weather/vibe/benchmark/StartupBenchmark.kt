package com.weather.vibe.benchmark

import androidx.benchmark.macro.BaselineProfileMode.Disable
import androidx.benchmark.macro.BaselineProfileMode.Require
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.CompilationMode.Full
import androidx.benchmark.macro.CompilationMode.None
import androidx.benchmark.macro.CompilationMode.Partial
import androidx.benchmark.macro.StartupMode.COLD
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.weather.vibe.benchmark.BaselineProfileMetrics.allMetrics
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Measures cold startup time across four compilation strategies.
 *
 * Compare the numbers to answer: "Is our Baseline Profile worth shipping?"
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class StartupBenchmark {

  @get:Rule
  val rule = MacrobenchmarkRule()

  /**
   * Worst case: no pre-compiled code at all. ART interprets
   * bytecode and JIT-compiles on the fly. Use this as the baseline
   * to compare other modes against.
   */
  @Test
  fun startupCompilationNone() =
    startup(None())

  /**
   * Partial compilation with the Baseline Profile ignored. Shows
   * what "Partial mode" gives you without our profile, so you can tell
   * how much of the win comes from the profile itself vs. just enabling
   * partial AOT.
   */
  @Test
  fun startupCompilationPartialBaselineDisabled() =
    startup(Partial(baselineProfileMode = Disable, warmupIterations = 1))

  /**
   * Partial compilation with the Baseline Profile required. This
   * is what real users experience after installing the app from Play
   * Store — the number to beat.
   */
  @Test
  fun startupCompilationBaselineProfile() =
    startup(Partial(baselineProfileMode = Require))

  /**
   * Theoretical best case: every method AOT-compiled. Fastest startup,
   * but the APK is larger and the first install on a device is slower.
   * Rarely shipped — useful as a ceiling to see how close our Baseline
   * Profile gets to the ideal.
   */
  @Test
  fun startupCompilationFull() =
    startup(Full())

  private fun startup(compilationMode: CompilationMode) {
    rule.measureRepeated(
      packageName = PACKAGE_NAME,
      metrics = allMetrics,
      compilationMode = compilationMode,
      iterations = 5,
      startupMode = COLD,
      setupBlock = { pressHome() },
      measureBlock = { startActivityAndWait() }
    )
  }
}
