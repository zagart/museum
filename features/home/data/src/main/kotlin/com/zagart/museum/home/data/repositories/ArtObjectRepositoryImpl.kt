package com.zagart.museum.home.data.repositories

import com.zagart.museum.home.data.extensions.dtosAsDomainList
import com.zagart.museum.home.data.extensions.entitiesAsDomainList
import com.zagart.museum.home.data.sources.ArtObjectLocalSource
import com.zagart.museum.home.data.sources.ArtObjectRemoteSource
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtObjectRepositoryImpl @Inject constructor(
    private val localSource: ArtObjectLocalSource,
    private val remoteSource: ArtObjectRemoteSource
) : ArtObjectRepository {

    override fun getArtObjects(): Flow<Result<List<ArtObject>>> {
        return flow {
            val cacheResult = localSource.getArtObjects().single()

            if (cacheResult.isFailure || cacheResult.getOrThrow().isEmpty()) {
                val networkResult = remoteSource.getArtObjects().single()

                if (networkResult.isSuccess) {
                    emit(Result.success(networkResult.getOrThrow().dtosAsDomainList()))
                } else {
                    emit(Result.failure((Throwable("Network data request failed"))))
                }
            } else {
                emit(Result.success(cacheResult.getOrThrow().entitiesAsDomainList()))
            }
        }
    }
}