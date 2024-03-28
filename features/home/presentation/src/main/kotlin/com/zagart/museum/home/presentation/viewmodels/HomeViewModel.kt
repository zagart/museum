package com.zagart.museum.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.usecases.GetArtObjectsByAuthorUseCase
import com.zagart.museum.home.domain.usecases.LoadMoreArtObjectsUseCase
import com.zagart.museum.home.domain.usecases.RefreshArtObjectsUseCase
import com.zagart.museum.home.presentation.extensions.toUiModel
import com.zagart.museum.home.presentation.models.HomeScreenModelUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
    private val loadMoreUseCase: LoadMoreArtObjectsUseCase,
    private val refreshUseCase: RefreshArtObjectsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.stateIn(
        scope = viewModelScope,
        initialValue = HomeScreenState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    private var loadMoreJob: Job? = null
    private var refreshJob: Job? = null

    init {
        viewModelScope.launch {
            artObjectsUseCase().collectLatest { artObjectsResult ->
                handleResult(artObjectsResult)

                if (artObjectsResult.isSuccess && artObjectsResult.getOrThrow()
                        .isEmpty() && refreshJob == null
                ) {
                    refresh(false, null)
                }
            }
        }
    }

    fun loadMore(size: Int = 0) {
        if (refreshJob != null) {
            return
        }

        loadMoreJob?.cancel()
        loadMoreJob = Job().also { job ->
            CoroutineScope(job).launch {
                updateAppendState()

                loadMoreUseCase(size).collectLatest { loadingResult ->
                    updateAppendState(loadingResult.isFailure)
                    loadMoreJob?.cancel()
                    loadMoreJob = null
                }
            }
        }
    }

    fun refresh(showLoading: Boolean, minResultTime: Long?) {
        if (showLoading) {
            _state.value = HomeScreenState.Loading
        }

        loadMoreJob?.cancel()
        refreshJob?.cancel()

        refreshJob = Job().also { job ->
            CoroutineScope(job).launch {
                val delayDef: Deferred<Unit>? =
                    if (minResultTime == null || minResultTime <= 0L) null else async {
                        delay(
                            minResultTime
                        )
                    }

                delayDef?.start()
                refreshUseCase.invoke().collectLatest { refreshResult ->
                    delayDef?.await()

                    if (refreshResult.isFailure) {
                        _state.value = HomeScreenState.Failure
                    }

                    refreshJob?.cancel()
                    refreshJob = null
                }
            }
        }
    }

    override fun onCleared() {
        loadMoreJob?.cancel()
        refreshJob?.cancel()
    }

    private fun handleResult(artObjectsResult: Result<List<ArtObject>>) {
        if (artObjectsResult.isSuccess) {
            _state.value =
                HomeScreenState.Success(items = artObjectsResult.getOrThrow().map { domainModel ->
                    domainModel.toUiModel()
                })
        } else {
            _state.value = HomeScreenState.Failure
        }
    }

    private fun updateAppendState(isAppendingFailed: Boolean = false) {
        val currentState = _state.value

        if (currentState is HomeScreenState.Success) {
            _state.update {
                currentState.copy(
                    isAppending = !currentState.isAppending, isAppendingFailed = isAppendingFailed
                )
            }
        }
    }
}

sealed interface HomeScreenState {

    data object Loading : HomeScreenState

    data object Failure : HomeScreenState

    data class Success(
        val items: List<HomeScreenModelUi>,
        val isAppending: Boolean = false,
        val isAppendingFailed: Boolean = false
    ) : HomeScreenState
}