package com.zagart.museum.details.data.repositories

import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.details.data.extensions.dtoAsDomainModel
import com.zagart.museum.details.data.extensions.entityAsDomainModel
import com.zagart.museum.details.data.sources.ArtObjectDetailsLocalSource
import com.zagart.museum.details.data.sources.ArtObjectDetailsRemoteSource
import com.zagart.museum.domain.repositories.ArtObjectDetailsRepository
import com.zagart.museum.home.domain.models.ArtObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class ArtObjectDetailsRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localSource: ArtObjectDetailsLocalSource,
    private val remoteSource: ArtObjectDetailsRemoteSource
) : ArtObjectDetailsRepository {

    override fun getByObjectNumber(objectNumber: String): Flow<Result<ArtObject>> {
        return flow {
            val cacheResult = localSource.getByObjectNumber(objectNumber).single()

            if (cacheResult.isSuccess) {
                emit(Result.success(cacheResult.getOrThrow().entityAsDomainModel()))
            } else {
                val networkResult = remoteSource.getByObjectNumber(objectNumber).single()

                if (networkResult.isSuccess) {
                    val dto = networkResult.getOrThrow()

                    localSource.upsert(dto)
                    emit(Result.success(dto.dtoAsDomainModel()))
                } else {
                    emit(Result.failure(Throwable("Failure during details loading for $objectNumber")))
                }
            }
        }.flowOn(ioDispatcher)
    }
}
