package com.weather.vibe.benchmark

import androidx.benchmark.macro.CompilationMode.Partial
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Measures how smoothly the forecast list scrolls on the Home screen.
 *
 * The benchmark launches the app, waits until weather data is loaded,
 * and then flings the forecast list down and up. Each rendered frame is
 * timed so you can tell whether scrolling stays under the 16.6ms / 8.3ms
 * frame budget.
 *
 * If any frame regularly exceeds the budget, the user perceives jank —
 * this is the signal to look for recompositions, inline allocations, or
 * expensive work on the main thread.
 *
 * Note: the app must reach the Loaded state (network data fetched) for
 * the forecast list to appear. Run on a device with internet access.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ForecastScrollBenchmark {

  @get:Rule
  val rule = MacrobenchmarkRule()

  @Test
  fun scrollForecastList() {
    rule.measureRepeated(
      packageName = PACKAGE_NAME,
      metrics = listOf(FrameTimingMetric()),
      compilationMode = Partial(),
      iterations = 5,
      setupBlock = {
        pressHome()
        startActivityAndWait()
        waitForForecastList()
      },
      measureBlock = { scrollForecastListDownUp() }
    )
  }

  private fun MacrobenchmarkScope.waitForForecastList() {
    device.waitAndFindObject(
      selector = By.res(FORECAST_LIST_TAG),
      timeout = CONTENT_TIMEOUT_MS
    )
  }

  private fun MacrobenchmarkScope.scrollForecastListDownUp() {
    val forecastList = device.waitAndFindObject(
      selector = By.res(FORECAST_LIST_TAG),
      timeout = CONTENT_TIMEOUT_MS
    )
    device.flingElementDownUp(forecastList)
  }

  private companion object {
    const val FORECAST_LIST_TAG = "forecast_list"
    const val CONTENT_TIMEOUT_MS = 10_000L
  }
}
