package com.weather.vibe.core.tracing

/**
 * Names of the app's trace sections emitted via [traceSection].
 *
 * Each constant is a contract between the code that opens the section
 * and the benchmark that measures it — keep them here so both sides
 * always reference the same string.
 */
object TraceSections {
  const val KOIN_INITIALIZATION = "KoinInitialization"
}
