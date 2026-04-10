package com.weather.vibe.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore

/**
 * Activity-scoped holder that owns the per-[androidx.navigation3.runtime.NavEntry] [ViewModelStore]
 * instances used by [ViewModelStoreNavEntryDecorator].
 *
 * It exists to make those stores survive configuration changes: because the holder itself is an
 * Android [ViewModel], its own [ViewModelStore] is retained by `ComponentActivity`'s
 * `NonConfigurationInstances` across recreations, and so is every entry store that lives inside
 * it. When the hosting Activity is truly destroyed, [onCleared] clears any stores that did not
 * already leave via `onPop`.
 */
internal class NavEntryStoresHolder : ViewModel() {

  val stores: MutableMap<Any, ViewModelStore> = mutableMapOf()

  override fun onCleared() {
    stores.values.forEach { it.clear() }
    stores.clear()
  }
}
