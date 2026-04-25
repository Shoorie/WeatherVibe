package com.weather.vibe.feature.viberating.presentation.rating

import com.weather.vibe.feature.viberating.presentation.rating.RatingCardStateFactory.Companion.DEFAULT_SLIDER_VALUE
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardStateFactory.Companion.NOTE_MAX_LENGTH
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Editing
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraft
import com.weather.vibe.testing.viberating.fixture.RatingEntryFixtures.ratingEntry
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class RatingCardStateFactoryTest {

  private val factory = RatingCardStateFactory()

  @Test
  fun `given today has no entries yet, then the card opens with the slider in the middle`() {

    val state = factory.fromTodayEntries(entries = emptyList())

    expectThat(state).isA<Editing>()
      .get { draft.sliderValue }.isEqualTo(DEFAULT_SLIDER_VALUE)
  }

  @Test
  fun `given today has no entries yet, then the slider is marked as untouched`() {

    val state = factory.fromTodayEntries(entries = emptyList())

    expectThat(state).isA<Editing>().get { draft.sliderTouched }.isFalse()
  }

  @Test
  fun `given today has no entries yet, then the entry counter shows zero`() {

    val state = factory.fromTodayEntries(entries = emptyList())

    expectThat(state).isA<Editing>().get { todayEntryCount }.isEqualTo(0)
  }

  @Test
  fun `given today already has ratings, then the entry counter shows how many were added`() {

    val state = factory.fromTodayEntries(
      entries = listOf(ratingEntry(id = 1), ratingEntry(id = 2), ratingEntry(id = 3))
    )

    expectThat(state).isA<Editing>().get { todayEntryCount }.isEqualTo(3)
  }

  @Test
  fun `given today already has ratings, then the slider still opens fresh for the next entry`() {

    val state = factory.fromTodayEntries(
      entries = listOf(ratingEntry(id = 1, rating = 5))
    )

    expectThat(state).isA<Editing>()
      .get { draft.sliderValue }.isEqualTo(DEFAULT_SLIDER_VALUE)
  }

  @Test
  fun `when the slider is moved, then the new value is remembered`() {

    val initial = factory.fromTodayEntries(entries = emptyList())

    val updated = factory.withSliderValue(current = initial, value = 4)

    expectThat(updated).isA<Editing>().get { draft.sliderValue }.isEqualTo(4)
  }

  @Test
  fun `when the slider is moved, then it is marked as touched`() {

    val initial = factory.fromTodayEntries(entries = emptyList())

    val updated = factory.withSliderValue(current = initial, value = 4)

    expectThat(updated).isA<Editing>().get { draft.sliderTouched }.isTrue()
  }

  @Test
  fun `when the note is typed, then the typed text is remembered`() {

    val initial = factory.fromTodayEntries(entries = emptyList())

    val updated = factory.withNoteValue(current = initial, value = "Świetna kawa rano")

    expectThat(updated).isA<Editing>().get { draft.note }.isEqualTo("Świetna kawa rano")
  }

  @Test
  fun `when too many characters are typed, then the note is cut to the limit`() {

    val initial = factory.fromTodayEntries(entries = emptyList())
    val tooLong = "x".repeat(NOTE_MAX_LENGTH + 50)

    val updated = factory.withNoteValue(current = initial, value = tooLong)

    expectThat(updated).isA<Editing>().get { draft.note.length }.isEqualTo(NOTE_MAX_LENGTH)
  }

  @Test
  fun `when the note is opened, then it is marked as expanded`() {

    val initial = factory.fromTodayEntries(entries = emptyList())

    val updated = factory.withNoteExpanded(current = initial, expanded = true)

    expectThat(updated).isA<Editing>().get { draft.noteExpanded }.isTrue()
  }

  @Test
  fun `when the note is hidden, then any typed text is cleared`() {

    val withNote = factory.withNoteValue(
      current = factory.withNoteExpanded(
        current = factory.fromTodayEntries(entries = emptyList()),
        expanded = true
      ),
      value = "Coś tam"
    )

    val collapsed = factory.withNoteExpanded(current = withNote, expanded = false)

    expectThat(collapsed).isA<Editing>().get { draft.note }.isEqualTo("")
  }

  @Test
  fun `when a save is in progress, then the slider value is preserved`() {

    val draft = RatingFormDraft(sliderValue = 4, sliderTouched = true, note = "", noteExpanded = false)

    val state = factory.saving(draft = draft, todayEntryCount = 0)

    expectThat(state).isA<Saving>().get { this.draft.sliderValue }.isEqualTo(4)
  }

  @Test
  fun `when saving fails, then the slider value is preserved for retry`() {

    val draft = RatingFormDraft(sliderValue = 2, sliderTouched = true, note = "", noteExpanded = false)

    val state = factory.saveError(draft = draft, todayEntryCount = 0)

    expectThat(state).isA<SaveError>().get { this.draft.sliderValue }.isEqualTo(2)
  }

  @Test
  fun `when saving fails, then any typed note is preserved for retry`() {

    val draft = RatingFormDraft(
      sliderValue = 3,
      sliderTouched = true,
      note = "Świetny dzień",
      noteExpanded = true
    )

    val state = factory.saveError(draft = draft, todayEntryCount = 0)

    expectThat(state).isA<SaveError>().get { this.draft.note }.isEqualTo("Świetny dzień")
  }

  @Test
  fun `after a save succeeds, then the form resets to the middle`() {

    val state = factory.afterSaveSuccess(todayEntryCount = 1)

    expectThat(state).isA<Editing>()
      .get { draft.sliderValue }.isEqualTo(DEFAULT_SLIDER_VALUE)
  }

  @Test
  fun `after a save succeeds, then the entry counter reflects the new total`() {

    val state = factory.afterSaveSuccess(todayEntryCount = 2)

    expectThat(state).isA<Editing>().get { todayEntryCount }.isEqualTo(2)
  }

  @Test
  fun `when a new rating is saved while the form is fresh, then the entry counter updates without losing the draft`() {

    val withDraft = factory.withSliderValue(
      current = factory.fromTodayEntries(entries = emptyList()),
      value = 5
    )

    val updated: RatingCardUiState = factory.withTodayCount(current = withDraft, count = 4)

    expectThat(updated).isA<Editing>()
      .and { get { draft.sliderValue }.isEqualTo(5) }
      .and { get { todayEntryCount }.isEqualTo(4) }
  }
}
