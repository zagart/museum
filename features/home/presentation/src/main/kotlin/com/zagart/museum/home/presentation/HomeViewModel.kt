package com.zagart.museum.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zagart.museum.home.domain.usecases.GetArtObjectsUseCase
import com.zagart.museum.home.presentation.extensions.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val artObjectsUseCase: GetArtObjectsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.stateIn(
        scope = viewModelScope,
        initialValue = HomeScreenState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    init {
        viewModelScope.launch {
            artObjectsUseCase().collectLatest { result ->
                if (result.isFailure) {
                    _state.value = HomeScreenState.Failure
                } else {
                    _state.value = HomeScreenState.Success(
                        items = result.getOrThrow().toUiModel()
                    )
                }
            }
        }
    }
}

sealed interface HomeScreenState {

    data object Loading : HomeScreenState
    data object Failure : HomeScreenState

    data class Success(
        val items: List<HomeScreenItem>
    ) : HomeScreenState
}