package com.weather.vibe.feature.activityplanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.usecase.BuildActivityPlan
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.domain.weather.usecase.ObserveCachedWeather
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerAction.ActivitySelect
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerAction.BackClick
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerAction.RetryClick
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerEvent.NavigateBack
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Error
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loading
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
internal class ActivityPlannerViewModel(
  @InjectedParam private val selectedLocation: Location,
  private val buildActivityPlan: BuildActivityPlan,
  private val getWeather: GetWeather,
  private val observeCachedWeather: ObserveCachedWeather,
  private val resources: ActivityPlannerResources,
  private val stateFactory: ActivityPlannerStateFactory
) : ViewModel() {

  private val coordinates = selectedLocation.toCoordinates()
  private val selectedActivity = MutableStateFlow(ActivityType.RUNNING)

  private val _state = MutableStateFlow<ActivityPlannerUiState>(Loading)
  val state: StateFlow<ActivityPlannerUiState> = _state.asStateFlow()

  private val _event = Channel<ActivityPlannerEvent>()
  val event: Flow<ActivityPlannerEvent> = _event.receiveAsFlow()

  private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    _state.update { Error(throwable.message ?: resources.defaultError()) }
  }

  init {
    observeCache()
    ensureCacheFilled()
  }

  fun dispatch(action: ActivityPlannerAction) {
    when (action) {
      is ActivitySelect -> onActivitySelect(action)
      is BackClick -> onBackClick()
      is RetryClick -> onRetryClick()
    }
  }

  private fun observeCache() {
    combine(
      observeCachedWeather(coordinates),
      selectedActivity,
      ::toState
    ).filterNotNull()
      .onEach { next -> _state.update { next } }
      .launchIn(viewModelScope)
  }

  private fun toState(weather: WeatherData?, activity: ActivityType): ActivityPlannerUiState? {

    if (weather == null) return null
    return stateFactory.create(buildActivityPlan(weather, activity))
  }

  private fun ensureCacheFilled() {
    viewModelScope.launch(errorHandler) {
      if (observeCachedWeather(coordinates).first() != null) return@launch
      getWeather(coordinates).first().getOrThrow()
    }
  }

  private fun onActivitySelect(action: ActivitySelect) {
    selectedActivity.update { action.type }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onRetryClick() {
    _state.update { Loading }
    viewModelScope.launch(errorHandler) {
      getWeather(coordinates).first().getOrThrow()
    }
  }

  private fun send(event: ActivityPlannerEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
