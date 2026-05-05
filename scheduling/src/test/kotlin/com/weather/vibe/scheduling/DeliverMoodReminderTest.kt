package com.weather.vibe.scheduling

import com.weather.vibe.domain.profile.model.ProfileSummary
import com.weather.vibe.domain.profile.usecase.ObserveProfile
import com.weather.vibe.domain.viberating.usecase.ObserveTodayEntries
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.NotificationChannelKind
import com.weather.vibe.notifications.notification.mood.MoodReminderNotificationFactory
import com.weather.vibe.scheduling.notification.MoodPickReceiver
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.viberating.fixture.RatingEntryFixtures.ratingEntry
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class DeliverMoodReminderTest {

  private val notificationFactory = mockk<MoodReminderNotificationFactory>()
  private val notifier = mockk<AlertNotifier>(relaxed = true)
  private val observeProfile = mockk<ObserveProfile>()
  private val observeTodayEntries = mockk<ObserveTodayEntries>()
  private val timeProvider = FakeTimeProvider()
  private val deliver = DeliverMoodReminder(
    notificationFactory = notificationFactory,
    notifier = notifier,
    observeProfile = observeProfile,
    observeTodayEntries = observeTodayEntries,
    timeProvider = timeProvider
  )

  @Before
  fun setUp() {
    every { observeProfile() } returns flowOf(BLANK_PROFILE)
    every {
      notificationFactory.createPrompt(
        username = any(),
        dayOfWeek = any(),
        receiverClass = MoodPickReceiver::class.java
      )
    } returns PROMPT
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given no mood logged today, when reminder delivered, then prompt notification posted`() =
    runTest {

      every { observeTodayEntries() } returns flowOf(emptyList())

      deliver()

      verify { notifier.post(PROMPT) }
    }

  @Test
  fun `given mood already logged today, when reminder delivered, then nothing posted`() = runTest {

    every { observeTodayEntries() } returns flowOf(listOf(ratingEntry()))

    deliver()

    verify(exactly = 0) { notifier.post(any()) }
  }

  @Test
  fun `given mood already logged today, when forced, then prompt still posted`() = runTest {

    every { observeTodayEntries() } returns flowOf(listOf(ratingEntry()))

    deliver(force = true)

    verify { notifier.post(PROMPT) }
  }

  @Test
  fun `given username set, when reminder delivered, then factory receives username`() = runTest {

    every { observeProfile() } returns flowOf(NAMED_PROFILE)
    every { observeTodayEntries() } returns flowOf(emptyList())

    deliver()

    verify {
      notificationFactory.createPrompt(
        username = NAMED_USERNAME,
        dayOfWeek = any(),
        receiverClass = any()
      )
    }
  }

  @Test
  fun `given blank username, when reminder delivered, then factory receives null username`() =
    runTest {

      every { observeTodayEntries() } returns flowOf(emptyList())

      deliver()

      verify {
        notificationFactory.createPrompt(
          username = null,
          dayOfWeek = any(),
          receiverClass = any()
        )
      }
    }

  private companion object {
    const val NAMED_USERNAME = "Adrian"
    val BLANK_PROFILE = ProfileSummary(username = "", usageDays = 1)
    val NAMED_PROFILE = ProfileSummary(username = NAMED_USERNAME, usageDays = 1)
    val PROMPT = AlertNotification(
      body = "body",
      id = 1200,
      kind = NotificationChannelKind.MOOD_REMINDER,
      title = "title"
    )
  }
}
