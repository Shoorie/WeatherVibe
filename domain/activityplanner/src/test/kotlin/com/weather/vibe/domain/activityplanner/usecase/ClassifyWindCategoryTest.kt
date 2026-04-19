package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.WindCategory.BREEZY
import com.weather.vibe.domain.activityplanner.model.WindCategory.CALM
import com.weather.vibe.domain.activityplanner.model.WindCategory.GUSTY
import com.weather.vibe.domain.activityplanner.model.WindCategory.WINDY
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ClassifyWindCategoryTest {

  private val classify = ClassifyWindCategory()

  @Test
  fun `given below 12 kmh, then calm returned`() {
    expectThat(classify(kmh = 6.0)).isEqualTo(CALM)
  }

  @Test
  fun `given 12 to 21 kmh, then breezy returned`() {
    expectThat(classify(kmh = 15.0)).isEqualTo(BREEZY)
  }

  @Test
  fun `given 22 to 31 kmh, then windy returned`() {
    expectThat(classify(kmh = 28.0)).isEqualTo(WINDY)
  }

  @Test
  fun `given 32 or more kmh, then gusty returned`() {
    expectThat(classify(kmh = 40.0)).isEqualTo(GUSTY)
  }
}
