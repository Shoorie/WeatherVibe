package com.weather.vibe.benchmark

import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction.DOWN
import androidx.test.uiautomator.Direction.UP
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

const val PACKAGE_NAME = "com.weather.vibe"

/**
 * Waits up to [timeout] ms for a UI element matching [selector]
 * to appear, then returns it.
 *
 * Throws a clear [AssertionError] instead of a [NullPointerException]
 * if the element never shows up — which makes it much easier to tell
 * "the UI isn't ready yet" apart from a real test bug.
 */
fun UiDevice.waitAndFindObject(selector: BySelector, timeout: Long): UiObject2 {
  if (!wait(Until.hasObject(selector), timeout)) {
    throw AssertionError("Element not found on screen in ${timeout}ms (selector=$selector)")
  }
  return findObject(selector)
}

/**
 * Flings the given scrollable element down and then back up,
 * waiting for idle between gestures. Used by scroll benchmarks to
 * exercise a realistic user scroll session.
 */
fun UiDevice.flingElementDownUp(element: UiObject2) {
  element.setGestureMargin(displayWidth / 5)
  element.fling(DOWN)
  waitForIdle()
  element.fling(UP)
  waitForIdle()
}
