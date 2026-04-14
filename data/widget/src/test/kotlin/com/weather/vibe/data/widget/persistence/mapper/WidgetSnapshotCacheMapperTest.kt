package com.weather.vibe.data.widget.persistence.mapper

import com.weather.vibe.domain.weather.model.WeatherCondition.UNKNOWN
import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.RAINY_SNAPSHOT
import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.SNAPSHOT
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class WidgetSnapshotCacheMapperTest {

  private val mapper = WidgetSnapshotCacheMapper(
    locationMapper = WidgetLocationEntryMapper(),
    suggestionMapper = WidgetSuggestionEntryMapper()
  )

  @Test
  fun `when snapshot round tripped, then all fields preserved`() {

    val entry = mapper.toEntry(SNAPSHOT)
    val restored = mapper.toDomain(entry)

    expectThat(restored).isEqualTo(SNAPSHOT)
  }

  @Test
  fun `given rainy snapshot, when round tripped, then condition and mood preserved`() {

    val entry = mapper.toEntry(RAINY_SNAPSHOT)
    val restored = mapper.toDomain(entry)

    expectThat(restored).isEqualTo(RAINY_SNAPSHOT)
  }

  @Test
  fun `given unknown condition name in entry, when decoded, then condition falls back to unknown`() {

    val entry = mapper.toEntry(SNAPSHOT)
      .toBuilder()
      .setConditionName("WHATEVER_NEW_WEATHER")
      .build()

    val restored = mapper.toDomain(entry)

    expectThat(restored.condition).isEqualTo(UNKNOWN)
  }
}
