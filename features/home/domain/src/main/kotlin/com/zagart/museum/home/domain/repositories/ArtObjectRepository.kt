package com.zagart.museum.home.domain.repositories

import com.zagart.museum.home.domain.models.ArtObject
import kotlinx.coroutines.flow.Flow

interface ArtObjectRepository {

    fun getArtObjects(): Flow<Result<List<ArtObject>>>
}