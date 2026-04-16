package com.weather.vibe.notifications.work

import com.weather.vibe.domain.alerts.usecase.GetMorningBriefText
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.brief.MorningBriefNotificationFactory
import com.weather.vibe.notifications.ui.MorningBriefResources
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.BRIEF_TEXT
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class DeliverMorningBriefTest {

  private val getMorningBriefText = mockk<GetMorningBriefText>()
  private val notifier = mockk<AlertNotifier>(relaxed = true)
  private val resources = mockk<MorningBriefResources>().apply {
    every { title() } returns BRIEF_TITLE
  }
  private val notificationFactory = MorningBriefNotificationFactory(resources = resources)
  private val deliver = DeliverMorningBrief(
    getMorningBriefText = getMorningBriefText,
    notificationFactory = notificationFactory,
    notifier = notifier
  )

  @Before
  fun setUp() {
    coEvery { getMorningBriefText() } returns BRIEF_TEXT
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when invoked, then notification posted with brief text as body`() = runTest {

    deliver()

    verify { notifier.post(match<AlertNotification> { it.body == BRIEF_TEXT }) }
  }

  @Test
  fun `when invoked, then notification carries morning brief title`() = runTest {

    deliver()

    verify { notifier.post(match<AlertNotification> { it.title == BRIEF_TITLE }) }
  }

  @Test
  fun `given brief text is null, when invoked, then nothing posted`() = runTest {

    coEvery { getMorningBriefText() } returns null

    deliver()

    verify(exactly = 0) { notifier.post(any()) }
  }

  private companion object {
    const val BRIEF_TITLE = "Today's vibe"
  }
}
