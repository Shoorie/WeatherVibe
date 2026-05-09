package com.weather.vibe.core.remoteconfig.data

import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_DEFAULT
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_REMOTE
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_STATIC
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.weather.vibe.core.remoteconfig.domain.flag.BooleanFeatureFlag
import com.weather.vibe.core.remoteconfig.domain.flag.StringFeatureFlag
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DefaultFeatureFlagsTest {

  private val remoteConfig = mockk<FirebaseRemoteConfig>(relaxed = true)
  private lateinit var featureFlags: DefaultFeatureFlags

  @Before
  fun setUp() {
    every { remoteConfig.setConfigSettingsAsync(any()) } returns mockk(relaxed = true)
    every { remoteConfig.fetchAndActivate() } returns mockk<Task<Boolean>>(relaxed = true)
    featureFlags = DefaultFeatureFlags(remoteConfig = remoteConfig)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when bool requested with remote source, then return remote value`() {
    every { remoteConfig.getValue(REMOTE_KEY) } returns valueWith(
      asBoolean = true,
      source = VALUE_SOURCE_REMOTE
    )
    val flag = BooleanFeatureFlag(default = false, key = REMOTE_KEY)

    val result = featureFlags.bool(flag)

    expectThat(result).isEqualTo(true)
  }

  @Test
  fun `when bool requested with default source, then return remote default`() {
    every { remoteConfig.getValue(REMOTE_KEY) } returns valueWith(
      asBoolean = true,
      source = VALUE_SOURCE_DEFAULT
    )
    val flag = BooleanFeatureFlag(default = false, key = REMOTE_KEY)

    val result = featureFlags.bool(flag)

    expectThat(result).isEqualTo(true)
  }

  @Test
  fun `when bool requested with static source, then return flag default`() {
    every { remoteConfig.getValue(REMOTE_KEY) } returns valueWith(
      asBoolean = false,
      source = VALUE_SOURCE_STATIC
    )
    val flag = BooleanFeatureFlag(default = true, key = REMOTE_KEY)

    val result = featureFlags.bool(flag)

    expectThat(result).isEqualTo(true)
  }

  @Test
  fun `when string requested with remote source, then return remote value`() {
    every { remoteConfig.getValue(REMOTE_KEY) } returns valueWith(
      asString = REMOTE_VALUE,
      source = VALUE_SOURCE_REMOTE
    )
    val flag = StringFeatureFlag(default = "default", key = REMOTE_KEY)

    val result = featureFlags.string(flag)

    expectThat(result).isEqualTo(REMOTE_VALUE)
  }

  @Test
  fun `when string requested with static source, then return flag default`() {
    every { remoteConfig.getValue(REMOTE_KEY) } returns valueWith(
      asString = "",
      source = VALUE_SOURCE_STATIC
    )
    val flag = StringFeatureFlag(default = REMOTE_VALUE, key = REMOTE_KEY)

    val result = featureFlags.string(flag)

    expectThat(result).isEqualTo(REMOTE_VALUE)
  }

  private fun valueWith(asBoolean: Boolean, source: Int): FirebaseRemoteConfigValue =
    mockk<FirebaseRemoteConfigValue>().apply {
      every { this@apply.source } returns source
      every { asBoolean() } returns asBoolean
    }

  private fun valueWith(asString: String, source: Int): FirebaseRemoteConfigValue =
    mockk<FirebaseRemoteConfigValue>().apply {
      every { this@apply.source } returns source
      every { asString() } returns asString
    }

  private companion object {
    const val REMOTE_KEY = "test_flag"
    const val REMOTE_VALUE = "expected_value"
  }
}
