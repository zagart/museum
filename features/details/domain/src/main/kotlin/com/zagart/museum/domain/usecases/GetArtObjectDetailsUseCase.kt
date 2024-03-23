package com.zagart.museum.domain.usecases

import com.zagart.museum.domain.repositories.ArtObjectDetailsRepository
import com.zagart.museum.home.domain.models.ArtObject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtObjectDetailsUseCase @Inject constructor(
    private val repository: ArtObjectDetailsRepository
) {

    operator fun invoke(objectNumber: String): Flow<Result<ArtObject>> {
        return repository.getByObjectNumber(objectNumber)
    }
}