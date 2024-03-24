package com.zagart.museum.home.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArtObjectsByAuthorUseCase @Inject constructor(
    private val repository: ArtObjectRepository
) {

    operator fun invoke(): Flow<PagingData<ArtObject>> {
        return repository.getArtObjectsPagingData().map { pagingData ->
            var currentAuthor = ""

            pagingData.map { artObject ->
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