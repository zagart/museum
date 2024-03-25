package com.zagart.museum.domain.repositories

import com.zagart.museum.home.domain.models.ArtObject
import kotlinx.coroutines.flow.Flow

interface ArtObjectDetailsRepository {

    suspend fun getByObjectNumber(objectNumber: String): Flow<Result<ArtObject>>
}