package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.UvCategory.HIGH
import com.weather.vibe.domain.activityplanner.model.UvCategory.LOW
import com.weather.vibe.domain.activityplanner.model.UvCategory.MODERATE
import com.weather.vibe.domain.activityplanner.model.UvCategory.VERY_HIGH
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ClassifyUvCategoryTest {

  private val classify = ClassifyUvCategory()

  @Test
  fun `given uv under 3, then low returned`() {
    expectThat(classify(uvIndex = 1.5)).isEqualTo(LOW)
  }

  @Test
  fun `given uv 3 to 5, then moderate returned`() {
    expectThat(classify(uvIndex = 4.0)).isEqualTo(MODERATE)
  }

  @Test
  fun `given uv 6 to 7, then high returned`() {
    expectThat(classify(uvIndex = 7.0)).isEqualTo(HIGH)
  }

  @Test
  fun `given uv 8 or more, then very high returned`() {
    expectThat(classify(uvIndex = 9.0)).isEqualTo(VERY_HIGH)
  }
}
