package com.weather.vibe.data.widget.persistence.mapper

import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.DEFAULT_LOCATION
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class WidgetLocationEntryMapperTest {

  private val mapper = WidgetLocationEntryMapper()

  @Test
  fun `when location round tripped, then all fields preserved`() {

    val entry = mapper.toEntry(DEFAULT_LOCATION)
    val restored = mapper.toDomain(entry)

    expectThat(restored).isEqualTo(DEFAULT_LOCATION)
  }

  @Test
  fun `given admin1 null, when location round tripped, then admin1 stays null`() {

    val withoutAdmin = DEFAULT_LOCATION.copy(admin1 = null)

    val entry = mapper.toEntry(withoutAdmin)
    val restored = mapper.toDomain(entry)

    expectThat(restored.admin1).isNull()
  }

  @Test
  fun `given blank admin1 entry, when decoded, then admin1 mapped to null`() {

    val entry = mapper.toEntry(DEFAULT_LOCATION.copy(admin1 = ""))

    val restored = mapper.toDomain(entry)

    expectThat(restored.admin1).isNull()
  }
}
