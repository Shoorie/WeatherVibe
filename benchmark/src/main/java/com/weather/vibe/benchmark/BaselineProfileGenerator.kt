package com.weather.vibe.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Records a Baseline Profile by walking the app's critical
 * startup path.
 *
 * The test is not a benchmark — it produces a text file listing
 * the classes and methods that were loaded during the journey below.
 * That file is packaged into the APK/AAB and Play Store (or profileinstaller)
 * uses it to AOT-compile the hot code at install time, so users don't pay the
 * JIT tax on their first launches.
 *
 * `includeInStartupProfile = true` additionally tells R8 to group these
 * classes together in the DEX files (Dex Layout Optimization), which reduces
 * page faults during startup.
 *
 * Run with `./gradlew generateBaselineProfile` — the output lands in
 * `app/src/release/generated/baselineProfiles/baseline-prof.txt` and should
 * be committed.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

  @get:Rule
  val rule = BaselineProfileRule()

  @Test
  fun generateBaselineProfile() {
    rule.collect(
      packageName = PACKAGE_NAME,
      includeInStartupProfile = true,
      profileBlock = {
        pressHome()
        startActivityAndWait()
        waitForHomeContent()
        flingForecastList()
        navigateBottomTab(label = LOCATIONS_LABEL)
        navigateBottomTab(label = PROFILE_LABEL)
        navigateBottomTab(label = HOME_LABEL)
      }
    )
  }

  private fun MacrobenchmarkScope.waitForHomeContent() {
    device.wait(Until.hasObject(By.res(FORECAST_LIST_TAG)), CONTENT_TIMEOUT_MS)
    device.waitForIdle()
  }

  private fun MacrobenchmarkScope.flingForecastList() {
    val list = device.findObject(By.res(FORECAST_LIST_TAG)) ?: return
    device.flingElementDownUp(list)
  }

  private fun MacrobenchmarkScope.navigateBottomTab(label: String) {
    if (!device.wait(Until.hasObject(By.desc(label)), TAB_TIMEOUT_MS)) return
    device.findObject(By.desc(label))?.click()
    device.waitForIdle()
  }

  private companion object {
    const val FORECAST_LIST_TAG = "forecast_list"
    const val HOME_LABEL = "Home"
    const val LOCATIONS_LABEL = "Locations"
    const val PROFILE_LABEL = "Profile"
    const val CONTENT_TIMEOUT_MS = 10_000L
    const val TAB_TIMEOUT_MS = 5_000L
  }
}
