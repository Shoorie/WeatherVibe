package com.weather.vibe.feature.home.ui

/**
 * Test tag values exposed via `Modifier.testTag(...)`
 * on the Home screen.
 *
 * Note: e.g. [FORECAST_LIST] is referenced by string in
 * `:benchmark:ForecastScrollBenchmark`.
 *
 * If you rename the value, update the benchmark too — otherwise
 * scroll measurements will silently stop finding the list.
 */
internal object HomeTestTags {
  const val FORECAST_LIST = "forecast_list"
}
