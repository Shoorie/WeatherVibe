package com.weather.vibe

private const val MAJOR_MULTIPLIER = 1_000_000
private const val MINOR_MULTIPLIER = 1_000

fun versionCodeFrom(versionName: String): Int {

  val (major, minor, patch) = versionName
    .substringBefore(delimiter = '-')
    .split('.')
    .map { it.toIntOrNull() ?: 0 }

  return major * MAJOR_MULTIPLIER + minor * MINOR_MULTIPLIER + patch
}
