package com.jetbrains.spacetutorial

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.spacetutorial.entity.RocketLaunch
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

/**
 * Android ViewModel for managing SpaceX rocket launch data and UI state.
 *
 * Handles loading launches from [SpaceXSDK] and exposes the current state
 * via Compose [State] for reactive UI updates. Automatically loads data
 * on initialization.
 *
 * Annotated with [@KoinViewModel] for Koin DI integration with Jetpack's
 * ViewModel lifecycle. Retrieved in Composables via `koinViewModel<RocketLaunchViewModel>()`.
 *
 * @param sdk The SpaceX SDK for fetching launch data, injected by Koin.
 *
 * @see RocketLaunchScreenState for the UI state model
 */
@KoinViewModel
class RocketLaunchViewModel(private val sdk: SpaceXSDK) : ViewModel() {
    private val _state = mutableStateOf(RocketLaunchScreenState())
    val state: State<RocketLaunchScreenState> = _state

    init {
        loadLaunches()
    }

    /**
     * Loads rocket launches from the SDK, forcing a network refresh.
     *
     * Updates [state] to show loading indicator, then fetches data and
     * updates the state with results or clears on error.
     */
    fun loadLaunches() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, launches = emptyList())
            try {
                val launches = sdk.getLaunches(forceReload = true)
                _state.value = _state.value.copy(isLoading = false, launches = launches)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, launches = emptyList())
            }
        }
    }
}

/**
 * UI state for the rocket launch screen.
 *
 * @property isLoading True when data is being fetched.
 * @property launches List of rocket launches to display.
 */
data class RocketLaunchScreenState(
    val isLoading: Boolean = false,
    val launches: List<RocketLaunch> = emptyList()
)