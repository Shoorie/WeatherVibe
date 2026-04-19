package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.core.time.TimeProvider
import org.koin.core.annotation.Factory
import java.time.LocalDate

@Factory
class IsDateToday(private val timeProvider: TimeProvider) {

  operator fun invoke(date: LocalDate): Boolean =
    date == timeProvider.today()
}
