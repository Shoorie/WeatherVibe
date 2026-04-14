package com.weather.vibe.data.widget.persistence.mapper

import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.DEFAULT_SUGGESTION
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class WidgetSuggestionEntryMapperTest {

  private val mapper = WidgetSuggestionEntryMapper()

  @Test
  fun `when suggestion round tripped, then all fields preserved`() {

    val entry = mapper.toEntry(DEFAULT_SUGGESTION)
    val restored = mapper.toDomain(entry)

    expectThat(restored).isEqualTo(DEFAULT_SUGGESTION)
  }

  @Test
  fun `given empty genres, when round tripped, then genres stays empty`() {

    val empty = DEFAULT_SUGGESTION.copy(genres = emptyList())

    val entry = mapper.toEntry(empty)
    val restored = mapper.toDomain(entry)

    expectThat(restored.genres).isEqualTo(emptyList())
  }
}
