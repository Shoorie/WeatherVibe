package com.weather.vibe.scheduling

import com.weather.vibe.domain.alerts.usecase.GetMorningBriefText
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.NotificationChannelKind
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.brief.MorningBriefNotificationFactory
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
  private val notificationFactory = mockk<MorningBriefNotificationFactory>()
  private val deliver = DeliverMorningBrief(
    getMorningBriefText = getMorningBriefText,
    notificationFactory = notificationFactory,
    notifier = notifier
  )

  @Before
  fun setUp() {
    coEvery { getMorningBriefText() } returns BRIEF_TEXT
    every { notificationFactory.create(BRIEF_TEXT) } returns briefNotification(body = BRIEF_TEXT)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when morning brief delivered, then notification posted with brief text as body`() =
    runTest {

      deliver()

      verify { notifier.post(match<AlertNotification> { it.body == BRIEF_TEXT }) }
    }

  @Test
  fun `when morning brief delivered, then notification carries morning brief title`() = runTest {

    every { notificationFactory.create(BRIEF_TEXT) } returns briefNotification(title = BRIEF_TITLE)

    deliver()

    verify { notifier.post(match<AlertNotification> { it.title == BRIEF_TITLE }) }
  }

  @Test
  fun `given brief text is null, when morning brief delivered, then nothing posted`() = runTest {

    coEvery { getMorningBriefText() } returns null

    deliver()

    verify(exactly = 0) { notifier.post(any()) }
  }

  private fun briefNotification(
    title: String = BRIEF_TITLE,
    body: String = BRIEF_TEXT
  ): AlertNotification = AlertNotification(
    body = body,
    id = NOTIFICATION_ID,
    kind = NotificationChannelKind.MORNING_BRIEF,
    title = title
  )

  private companion object {
    const val BRIEF_TITLE = "Today's vibe"
    const val NOTIFICATION_ID = 1100
  }
}
