package com.weather.vibe.domain.viberating.model

import com.weather.vibe.testing.viberating.fixture.RatingEntryFixtures.ratingEntry
import org.junit.Test
import strikt.api.expectCatching
import strikt.assertions.isFailure
import strikt.assertions.isSuccess

class RatingEntryTest {

  @Test
  fun `when rating is below minimum, then construction fails`() {

    expectCatching { ratingEntry(rating = 0) }.isFailure()
  }

  @Test
  fun `when rating is above maximum, then construction fails`() {

    expectCatching { ratingEntry(rating = 6) }.isFailure()
  }

  @Test
  fun `when rating is within bounds, then construction succeeds`() {

    expectCatching { ratingEntry(rating = 3) }.isSuccess()
  }

  @Test
  fun `given the note is empty, then construction succeeds`() {

    expectCatching { ratingEntry(note = null) }.isSuccess()
  }

  @Test
  fun `given the note fits the limit, then construction succeeds`() {

    val noteAtLimit = "x".repeat(RatingEntry.NOTE_MAX_LENGTH)

    expectCatching { ratingEntry(note = noteAtLimit) }.isSuccess()
  }

  @Test
  fun `given the note exceeds the limit, then construction fails`() {

    val tooLongNote = "x".repeat(RatingEntry.NOTE_MAX_LENGTH + 1)

    expectCatching { ratingEntry(note = tooLongNote) }.isFailure()
  }
}
