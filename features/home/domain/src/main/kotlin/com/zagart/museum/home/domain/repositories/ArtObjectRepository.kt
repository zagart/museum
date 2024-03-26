package com.zagart.museum.home.domain.repositories

import com.zagart.museum.home.domain.models.ArtObject
import kotlinx.coroutines.flow.Flow

interface ArtObjectRepository {

    suspend fun getAllArtObjects(): Flow<Result<List<ArtObject>>>

    suspend fun loadMore(size : Int): Flow<Result<Unit>>
}