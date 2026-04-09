package com.weather.vibe.core.time

import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime

@Factory(binds = [TimeProvider::class])
internal class RealTimeProvider : TimeProvider {

  override fun now(): LocalDateTime =
    LocalDateTime.now()

  override fun today(): LocalDate =
    LocalDate.now()

  override fun nowEpochMillis(): Long =
    System.currentTimeMillis()
}
