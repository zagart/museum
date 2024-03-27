package com.zagart.museum.home.domain.usecases

import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArtObjectsByAuthorUseCase @Inject constructor(
    private val repository: ArtObjectRepository
) {

    suspend operator fun invoke(): Flow<Result<List<ArtObject>>> {
        return repository.getAll().map { result ->
            var currentAuthor = ""

            result.map { domainModels ->
                domainModels.map { artObject ->
                    if (artObject.principalOrFirstMaker != currentAuthor) {
                        currentAuthor = artObject.principalOrFirstMaker
                        artObject.copy(withAuthorHeader = true)
                    } else {
                        artObject
                    }
                }
            }
        }
    }
}