package com.zagart.museum.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.usecases.GetArtObjectsByAuthorUseCase
import com.zagart.museum.home.domain.usecases.LoadMoreArtObjectsUseCase
import com.zagart.museum.home.presentation.extensions.toUiModel
import com.zagart.museum.home.presentation.models.HomeScreenModelUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    artObjectsUseCase: GetArtObjectsByAuthorUseCase,
    private val loadMoreUseCase: LoadMoreArtObjectsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.stateIn(
        scope = viewModelScope,
        initialValue = HomeScreenState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    private var loadingJob: Job? = null

    init {
        viewModelScope.launch {
            artObjectsUseCase().collectLatest { artObjectsResult ->
                handleResult(artObjectsResult)

                if (artObjectsResult.isSuccess && artObjectsResult.getOrThrow().isEmpty()) {
                    loadMore()
                }
            }
        }
    }

    fun loadMore(size: Int = 0) {
        loadingJob?.cancel()
        loadingJob = Job().also { job ->
            CoroutineScope(job).launch {
                toggleAppendingState()

                loadMoreUseCase(size).collectLatest { loadingResult ->
                    toggleAppendingState(loadingResult.isFailure)
                    loadingJob?.cancel()
                }
            }
        }
    }

    private fun handleResult(artObjectsResult: Result<List<ArtObject>>) {
        if (artObjectsResult.isSuccess) {
            _state.value = HomeScreenState.Success(
                isAppending = false,
                isAppendingFailed = false,
                items = artObjectsResult.getOrThrow().map { domainModel ->
                    domainModel.toUiModel()
                }
            )
        } else {
            _state.value = HomeScreenState.Failure
        }
    }

    private fun toggleAppendingState(isAppendingFailed: Boolean = false) {
        val currentState = _state.value

        if (currentState is HomeScreenState.Success) {
            _state.update {
                currentState.copy(
                    isAppending = !currentState.isAppending,
                    isAppendingFailed = isAppendingFailed
                )
            }
        }
    }
}

sealed interface HomeScreenState {

    data object Loading : HomeScreenState

    data object Failure : HomeScreenState

    data class Success(
        val isAppending: Boolean,
        val isAppendingFailed: Boolean,
        val items: List<HomeScreenModelUi>
    ) : HomeScreenState
}