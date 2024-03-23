package com.zagart.museum.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zagart.museum.home.domain.usecases.GetArtObjectsUseCase
import com.zagart.museum.home.presentation.extensions.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    artObjectsUseCase: GetArtObjectsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.stateIn(
        scope = viewModelScope,
        initialValue = HomeScreenState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    init {
        _state.value = HomeScreenState.Success(
            artObjectsPagingData = artObjectsUseCase().map { pagingData ->
                pagingData.map { artObject ->
                    artObject.toUiModel()
                }
            }.cachedIn(viewModelScope)
        )
    }
}

sealed interface HomeScreenState {

    data object Loading : HomeScreenState
    data object Failure : HomeScreenState

    data class Success(
        val artObjectsPagingData: Flow<PagingData<HomeScreenItemModel>>
    ) : HomeScreenState
}