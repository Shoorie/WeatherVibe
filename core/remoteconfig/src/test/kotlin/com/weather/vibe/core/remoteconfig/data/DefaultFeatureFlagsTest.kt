package com.weather.vibe.core.remoteconfig.data

import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_DEFAULT
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_REMOTE
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_STATIC
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.weather.vibe.core.remoteconfig.fixture.FeatureFlagFixtures.FLAG_KEY
import com.weather.vibe.core.remoteconfig.fixture.FeatureFlagFixtures.booleanFlag
import com.weather.vibe.core.remoteconfig.fixture.FeatureFlagFixtures.stringFlag
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
  fun `given remote source, when bool read, then return remote value`() {
    every { remoteConfig.getValue(FLAG_KEY) } returns booleanValue(
      asBoolean = true,
      source = VALUE_SOURCE_REMOTE
    )

    val result = featureFlags.bool(booleanFlag(default = false))

    expectThat(result).isEqualTo(true)
  }

  @Test
  fun `given default source, when bool read, then return remote default`() {
    every { remoteConfig.getValue(FLAG_KEY) } returns booleanValue(
      asBoolean = true,
      source = VALUE_SOURCE_DEFAULT
    )

    val result = featureFlags.bool(booleanFlag(default = false))

    expectThat(result).isEqualTo(true)
  }

  @Test
  fun `given static source, when bool read, then return flag default`() {
    every { remoteConfig.getValue(FLAG_KEY) } returns booleanValue(
      asBoolean = false,
      source = VALUE_SOURCE_STATIC
    )

    val result = featureFlags.bool(booleanFlag(default = true))

    expectThat(result).isEqualTo(true)
  }

  @Test
  fun `given remote source, when string read, then return remote value`() {
    every { remoteConfig.getValue(FLAG_KEY) } returns stringValue(
      asString = REMOTE_VALUE,
      source = VALUE_SOURCE_REMOTE
    )

    val result = featureFlags.string(stringFlag(default = "irrelevant"))

    expectThat(result).isEqualTo(REMOTE_VALUE)
  }

  @Test
  fun `given static source, when string read, then return flag default`() {
    every { remoteConfig.getValue(FLAG_KEY) } returns stringValue(
      asString = "",
      source = VALUE_SOURCE_STATIC
    )

    val result = featureFlags.string(stringFlag(default = REMOTE_VALUE))

    expectThat(result).isEqualTo(REMOTE_VALUE)
  }

  private fun booleanValue(asBoolean: Boolean, source: Int): FirebaseRemoteConfigValue =
    mockk<FirebaseRemoteConfigValue>().apply {
      every { this@apply.source } returns source
      every { asBoolean() } returns asBoolean
    }

  private fun stringValue(asString: String, source: Int): FirebaseRemoteConfigValue =
    mockk<FirebaseRemoteConfigValue>().apply {
      every { this@apply.source } returns source
      every { asString() } returns asString
    }

  private companion object {
    const val REMOTE_VALUE = "expected_value"
  }
}
