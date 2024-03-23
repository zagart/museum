package com.zagart.museum.home.domain.usecases

import androidx.paging.PagingData
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtObjectsUseCase @Inject constructor(
    private val repository: ArtObjectRepository
) {

    operator fun invoke(): Flow<PagingData<ArtObject>> {
        return repository.getArtObjectsPagingData()
    }
}