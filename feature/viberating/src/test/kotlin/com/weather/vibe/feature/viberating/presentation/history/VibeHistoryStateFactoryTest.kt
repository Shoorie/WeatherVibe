package com.weather.vibe.feature.viberating.presentation.history

import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.domain.weather.model.Condition.SUNNY
import com.weather.vibe.domain.viberating.model.ConditionAverage
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.VibeStats
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryStateFactory.Companion.PATTERNS_UNLOCK_THRESHOLD
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Day
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Empty
import com.weather.vibe.feature.viberating.presentation.history.state.PatternsSectionUiState
import com.weather.vibe.testing.viberating.fixture.RatingEntryFixtures.ratingEntry
import com.weather.vibe.testing.viberating.fixture.WeatherSnapshotFixtures
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNotNull
import strikt.assertions.isTrue
import java.time.LocalDate
import java.time.YearMonth

class VibeHistoryStateFactoryTest {

  private val factory = VibeHistoryStateFactory()

  @Test
  fun `when a month is shown, then the calendar always has six full weeks`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.cells).hasSize(SIX_WEEKS_TOTAL_CELLS)
  }

  @Test
  fun `given April 2026 starts on a Wednesday, then the first two slots are blank`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.cells.take(LEADING_BLANKS_FOR_APRIL_2026))
      .containsExactly(Empty, Empty)
  }

  @Test
  fun `given a single rating exists, then that day shows the saved rating`() {

    val entry = ratingEntry(date = APRIL_15, rating = 4, weather = WeatherSnapshotFixtures.SUNNY_20C)

    val state = factory.create(
      entriesByDate = mapOf(APRIL_15 to listOf(entry)),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    val day = state.cells.filterIsInstance<Day>().single { it.date == APRIL_15 }
    expectThat(day.rating).isEqualTo(4)
  }

  @Test
  fun `given several ratings exist on the same day, then that day shows the rounded average`() {

    val morning = ratingEntry(date = APRIL_15, rating = 5, createdAtEpochMs = 1L)
    val evening = ratingEntry(date = APRIL_15, rating = 2, createdAtEpochMs = 2L)

    val state = factory.create(
      entriesByDate = mapOf(APRIL_15 to listOf(morning, evening)),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    val day = state.cells.filterIsInstance<Day>().single { it.date == APRIL_15 }
    expectThat(day.rating).isEqualTo(4)
  }

  @Test
  fun `given several ratings exist on the same day, then that day shows how many were added`() {

    val morning = ratingEntry(date = APRIL_15, rating = 5, createdAtEpochMs = 1L)
    val evening = ratingEntry(date = APRIL_15, rating = 2, createdAtEpochMs = 2L)

    val state = factory.create(
      entriesByDate = mapOf(APRIL_15 to listOf(morning, evening)),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    val day = state.cells.filterIsInstance<Day>().single { it.date == APRIL_15 }
    expectThat(day.entryCount).isEqualTo(2)
  }

  @Test
  fun `then today is highlighted on the calendar`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    val day = state.cells.filterIsInstance<Day>().single { it.date == APRIL_15 }
    expectThat(day.isToday).isTrue()
  }

  @Test
  fun `given a day is in the future, then it is marked as not yet available`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    val tomorrow = state.cells.filterIsInstance<Day>().single { it.date == APRIL_15.plusDays(1) }
    expectThat(tomorrow.isFuture).isTrue()
  }

  @Test
  fun `given a past month is shown, then jumping to the next month is allowed`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = MARCH_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.canNavigateNext).isTrue()
  }

  @Test
  fun `given the current month is shown, then jumping to the future is blocked`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.canNavigateNext).isFalse()
  }

  @Test
  fun `when a day is tapped on the calendar, then that day is marked as selected`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = APRIL_15
    )

    val day = state.cells.filterIsInstance<Day>().single { it.date == APRIL_15 }
    expectThat(day.isSelected).isTrue()
  }

  @Test
  fun `when a day with several ratings is opened, then every rating appears in the day details`() {

    val morning = ratingEntry(date = APRIL_15, rating = 5, createdAtEpochMs = 1L)
    val evening = ratingEntry(date = APRIL_15, rating = 3, createdAtEpochMs = 2L)

    val state = factory.create(
      entriesByDate = mapOf(APRIL_15 to listOf(morning, evening)),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = APRIL_15
    )

    val detail = state.selectedDayDetail
    expectThat(detail).isNotNull()
    expectThat(detail!!.entries).hasSize(2)
  }

  @Test
  fun `when a day is opened, then the latest rating appears at the top of the day details`() {

    val earlier = ratingEntry(date = APRIL_15, rating = 5, createdAtEpochMs = 100L)
    val later = ratingEntry(date = APRIL_15, rating = 3, createdAtEpochMs = 999L)

    val state = factory.create(
      entriesByDate = mapOf(APRIL_15 to listOf(earlier, later)),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = APRIL_15
    )

    val detail = state.selectedDayDetail
    expectThat(detail).isNotNull()
    expectThat(detail!!.entries.first().rating).isEqualTo(later.rating)
  }

  @Test
  fun `when a day is opened, then the saved note is shown alongside the rating`() {

    val entry = ratingEntry(
      date = APRIL_15,
      rating = 4,
      weather = WeatherSnapshotFixtures.SUNNY_20C,
      note = "Świetny spacer"
    )

    val state = factory.create(
      entriesByDate = mapOf(APRIL_15 to listOf(entry)),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = APRIL_15
    )

    val detail = state.selectedDayDetail
    expectThat(detail).isNotNull()
    expectThat(detail!!.entries.single().note).isEqualTo("Świetny spacer")
  }

  @Test
  fun `when a day is opened, then a blank note is shown as no note at all`() {

    val entry = ratingEntry(date = APRIL_15, rating = 4, note = "   ")

    val state = factory.create(
      entriesByDate = mapOf(APRIL_15 to listOf(entry)),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = APRIL_15
    )

    val detail = state.selectedDayDetail
    expectThat(detail).isNotNull()
    expectThat(detail!!.entries.single().note).isEqualTo(null)
  }

  @Test
  fun `given the user has not rated yet, then the patterns section is hidden`() {

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = VibeStats.EMPTY,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.patterns).isEqualTo(PatternsSectionUiState.Hidden)
  }

  @Test
  fun `given there are not enough ratings yet, then the patterns section shows how many more are needed`() {

    val stats = VibeStats(
      averageRating = 4.0,
      totalEntries = 5,
      uniqueDayCount = 5,
      favoriteCondition = SUNNY,
      conditionAverages = listOf(
        ConditionAverage(condition = SUNNY, averageRating = 4.0, entryCount = 5)
      )
    )

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = stats,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.patterns).isA<PatternsSectionUiState.Locked>()
      .and { get { entriesSoFar }.isEqualTo(5) }
      .and { get { entriesNeeded }.isEqualTo(PATTERNS_UNLOCK_THRESHOLD - 5) }
  }

  @Test
  fun `given the user reached the threshold, then the patterns section unlocks the ranking`() {

    val stats = unlockedStats(totalEntries = PATTERNS_UNLOCK_THRESHOLD)

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = stats,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.patterns).isA<PatternsSectionUiState.Unlocked>()
      .get { basedOnEntries }.isEqualTo(PATTERNS_UNLOCK_THRESHOLD)
  }

  @Test
  fun `given the best weather averages five, then a weather averaging two and a half fills the bar halfway`() {

    val stats = unlockedStats(
      totalEntries = PATTERNS_UNLOCK_THRESHOLD,
      conditionAverages = listOf(
        ConditionAverage(condition = SUNNY, averageRating = 4.0, entryCount = 7),
        ConditionAverage(condition = Condition.RAIN, averageRating = 2.0, entryCount = 7)
      )
    )

    val state = factory.create(
      entriesByDate = emptyMap(),
      stats = stats,
      viewMonth = APRIL_2026,
      currentMonth = APRIL_2026,
      today = APRIL_15,
      selectedDate = null
    )

    expectThat(state.patterns).isA<PatternsSectionUiState.Unlocked>()
      .get { ranking.map { it.progressFraction } }
      .containsExactly(1f, 0.5f)
  }

  private fun unlockedStats(
    totalEntries: Int,
    conditionAverages: List<ConditionAverage> = listOf(
      ConditionAverage(condition = SUNNY, averageRating = 4.0, entryCount = totalEntries)
    )
  ): VibeStats = VibeStats(
    averageRating = 4.0,
    totalEntries = totalEntries,
    uniqueDayCount = totalEntries,
    favoriteCondition = SUNNY,
    conditionAverages = conditionAverages
  )

  private companion object {
    val APRIL_2026: YearMonth = YearMonth.of(2026, 4)
    val MARCH_2026: YearMonth = YearMonth.of(2026, 3)
    val APRIL_15: LocalDate = LocalDate.of(2026, 4, 15)
    const val SIX_WEEKS_TOTAL_CELLS: Int = 42
    const val LEADING_BLANKS_FOR_APRIL_2026: Int = 2
  }
}
