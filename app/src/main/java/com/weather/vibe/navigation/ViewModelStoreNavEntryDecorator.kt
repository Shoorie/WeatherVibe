package com.weather.vibe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntryDecorator

/**
 * Returns a [ViewModelStoreNavEntryDecorator] that scopes a [ViewModelStore] to each
 * [androidx.navigation3.runtime.NavEntry.contentKey]. ViewModels obtained inside an entry
 * (e.g. via koinViewModel) then live only as long as that entry stays on the backstack,
 * and are cleared when the entry is popped.
 *
 * The per-entry stores are parked inside an Activity-scoped [NavEntryStoresHolder] so that
 * they survive configuration changes the same way an ordinary Activity-scoped ViewModel does.
 *
 * Nav3 1.0.1 does not ship a ViewModelStore decorator out of the box — without one, every
 * entry falls back to the Activity's [LocalViewModelStoreOwner] and ViewModels effectively
 * become Activity-scoped singletons across the whole app.
 */
@Composable
fun <T : Any> rememberViewModelStoreNavEntryDecorator(): ViewModelStoreNavEntryDecorator<T> {
  val holder: NavEntryStoresHolder = viewModel()
  return remember(holder) { ViewModelStoreNavEntryDecorator(holder.stores) }
}

class ViewModelStoreNavEntryDecorator<T : Any> internal constructor(
  stores: MutableMap<Any, ViewModelStore>
) : NavEntryDecorator<T>(
  onPop = { contentKey -> stores.remove(contentKey)?.clear() },
  decorate = { entry ->
    val store = stores.getOrPut(entry.contentKey) { ViewModelStore() }
    val owner = remember(store) { NavEntryViewModelStoreOwner(store) }
    CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
      entry.Content()
    }
  }
)

private class NavEntryViewModelStoreOwner(
  override val viewModelStore: ViewModelStore
) : ViewModelStoreOwner
