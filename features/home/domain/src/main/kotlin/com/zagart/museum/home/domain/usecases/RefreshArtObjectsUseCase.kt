package com.zagart.museum.home.domain.usecases

import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class RefreshArtObjectsUseCase @Inject constructor(
    private val repository: ArtObjectRepository,
) {

    suspend fun invoke(): Flow<Result<Unit>> {
        return repository.removeAll().map { removeResult ->
            return@map if (removeResult.isSuccess) {
                repository.loadMore(0).single().map { }
            } else {
                removeResult
            }
        }
    }
}