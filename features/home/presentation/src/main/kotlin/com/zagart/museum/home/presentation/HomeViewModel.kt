package com.zagart.museum.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zagart.museum.home.domain.usecases.GetArtObjectsByAuthorUseCase
import com.zagart.museum.home.presentation.extensions.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    artObjectsUseCase: GetArtObjectsByAuthorUseCase
) : ViewModel() {

    //PagingData provides state handling, so we do not need to have own one
    val artObjectsPagingData: Flow<PagingData<HomeScreenItemModel>>

    init {
        artObjectsPagingData = artObjectsUseCase().map { pagingData ->
            pagingData.map { artObject ->
                artObject.toUiModel()
            }
        }.cachedIn(viewModelScope)
    }
}