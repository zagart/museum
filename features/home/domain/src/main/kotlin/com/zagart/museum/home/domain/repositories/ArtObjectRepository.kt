package com.zagart.museum.home.domain.repositories

import androidx.paging.PagingData
import com.zagart.museum.home.domain.models.ArtObject
import kotlinx.coroutines.flow.Flow

interface ArtObjectRepository {

    fun getArtObjectsPagingData(): Flow<PagingData<ArtObject>>
}