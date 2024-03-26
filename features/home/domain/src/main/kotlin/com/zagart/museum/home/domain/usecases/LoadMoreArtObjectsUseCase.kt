package com.zagart.museum.home.domain.usecases

import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadMoreArtObjectsUseCase @Inject constructor(private val repository: ArtObjectRepository) {

    suspend operator fun invoke(size: Int): Flow<Result<Unit>> {
        return repository.loadMore(size)
    }
}