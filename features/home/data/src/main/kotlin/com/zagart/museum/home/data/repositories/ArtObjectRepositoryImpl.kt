package com.zagart.museum.home.data.repositories

import com.zagart.museum.api.PAGE_SIZE
import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.home.data.extensions.toDomainModel
import com.zagart.museum.home.data.sources.ArtObjectLocalSource
import com.zagart.museum.home.data.sources.ArtObjectRemoteSource
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class ArtObjectRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localSource: ArtObjectLocalSource,
    private val remoteSource: ArtObjectRemoteSource
) : ArtObjectRepository {

    override suspend fun getAll(): Flow<Result<List<ArtObject>>> {
        return localSource.getAll().map { result ->
            result.map { entities ->
                entities.map { entity ->
                    entity.toDomainModel()
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun loadMore(size: Int): Flow<Result<Unit>> {
        val page = calculatePage(size)
        val networkResult = remoteSource.getByPage(page = page, pageSize = PAGE_SIZE).single()

        if (networkResult.isSuccess) {
            val dtos = networkResult.getOrThrow()

            localSource.insertAll(dtos).single().let { insertionResult ->
                if (insertionResult.isFailure) {
                    return flow { emit(insertionResult) }
                }
            }
        }

        return flow { emit(networkResult.map { }) }
    }

    override suspend fun removeAll(): Flow<Result<Unit>> {
        return localSource.removeAll()
    }

    private fun calculatePage(size: Int): Int {
        return if (size < PAGE_SIZE) 1 else (size / PAGE_SIZE) + 1
    }
}