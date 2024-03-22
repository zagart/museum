package com.zagart.museum.home.data.sources

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.home.data.daos.ArtObjectDao
import com.zagart.museum.home.data.extensions.dtosAtEntityList
import com.zagart.museum.home.data.models.ArtObjectEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtObjectLocalSource @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val artObjectDao: ArtObjectDao
) {

    fun getAll(): Flow<Result<List<ArtObjectEntity>>> {
        return flow {
            emit(runCatching {
                artObjectDao.getAll()
            })
        }.flowOn(ioDispatcher)
    }

    fun insertAll(entities: List<ArtObjectDto>): Flow<Result<Unit>> {
        return flow {
            emit(runCatching {
                artObjectDao.insertAll(entities.dtosAtEntityList())
            })
        }
    }
}
