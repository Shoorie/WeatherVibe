package com.weather.vibe.core.tracing

import android.os.Trace

/**
 * Wraps [block] in a named `Trace.beginSection` / `Trace.endSection` pair.
 *
 * The section is always closed, even if [block] throws — so Perfetto
 * traces stay balanced regardless of failures.
 *
 * Use the emitted label together with a `TraceSectionMetric(label)` in
 * a macrobenchmark to measure how long any specific section of code takes
 * during a cold start or user journey.
 */
inline fun <T> traceSection(label: String, block: () -> T): T {
  Trace.beginSection(label)
  try {
    return block()
  } finally {
    Trace.endSection()
  }
}
