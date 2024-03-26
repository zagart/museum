package com.zagart.museum.home.data.sources

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.home.data.daos.ArtObjectShortDao
import com.zagart.museum.home.data.extensions.dtosAtEntityList
import com.zagart.museum.home.data.models.ArtObjectShortEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtObjectLocalSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val artObjectDao: ArtObjectShortDao
) {

    internal fun getAll(): Flow<Result<List<ArtObjectShortEntity>>> {
        return artObjectDao.getAll().map { entities ->
            runCatching { entities }
        }.flowOn(ioDispatcher)
    }

    internal fun insertAll(
        artObjects: List<ArtObjectDto>
    ): Flow<Result<Unit>> {
        return flow {
            emit(runCatching {
                artObjectDao.insertAll(artObjects.dtosAtEntityList())
            })
        }.flowOn(ioDispatcher)
    }

    internal fun removeAll(): Flow<Result<Unit>> {
        return flow {
            emit(runCatching {
                artObjectDao.removeAll()
            })
        }.flowOn(ioDispatcher)
    }

    internal fun getCacheCreationTime(): Flow<Result<Long>> {
        return flow {
            emit(runCatching {
                artObjectDao.getCacheCreationDate()
            })
        }.flowOn(ioDispatcher)
    }

    fun getCount(): Flow<Result<Int>> {
        return flow {
            emit(runCatching { artObjectDao.getCount() })
        }.flowOn(ioDispatcher)
    }
}