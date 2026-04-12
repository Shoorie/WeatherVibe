package com.weather.vibe.benchmark

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric

/**
 * Bundle of metrics that together show what Baseline Profiles
 * actually accelerate.
 *
 * Compare these numbers across compilation modes to see where
 * Baseline Profiles pay off.
 */
@OptIn(ExperimentalMetricApi::class)
object BaselineProfileMetrics {

  /**
   * Total time from launch to the first rendered frame
   * (Time To Initial Display).
   */
  private val startupTimingMetric = StartupTimingMetric()

  /**
   * Time ART spent JIT-compiling methods that weren't in the
   * Baseline Profile. A good profile covers the startup path,
   * so this number should be close to zero.
   */
  private val jitCompilationMetric =
    TraceSectionMetric("JIT Compiling %", label = "JIT compilation")

  /**
   * Time spent initializing classes for the first time during startup.
   * Dex Layout Optimization (enabled via `includeInStartupProfile = true`
   * in the generator) groups startup classes together in the DEX, which
   * reduces page faults and shrinks this.
   */
  private val classInitMetric =
    TraceSectionMetric("L%/%;", label = "ClassInit")

  val allMetrics = listOf(
    startupTimingMetric,
    jitCompilationMetric,
    classInitMetric
  )
}
