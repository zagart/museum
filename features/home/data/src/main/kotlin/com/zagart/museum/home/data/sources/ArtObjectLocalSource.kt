package com.zagart.museum.home.data.sources

import androidx.paging.PagingSource
import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.home.data.daos.ArtObjectDao
import com.zagart.museum.home.data.daos.RemoteKeyDao
import com.zagart.museum.home.data.extensions.dtosAtEntityList
import com.zagart.museum.home.data.models.ArtObjectEntity
import com.zagart.museum.home.data.models.RemoteKeyEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtObjectLocalSource @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val artObjectDao: ArtObjectDao,
    private val remoteKeyDao: RemoteKeyDao,
) {

    internal fun getAll(): PagingSource<Int, ArtObjectEntity> {
        return artObjectDao.getAll()
    }

    internal fun insertPage(
        page: Int,
        artObjects: List<ArtObjectDto>,
        keys: List<RemoteKeyEntity>
    ): Flow<Result<Unit>> {
        return flow {
            emit(runCatching {
                artObjectDao.insertAll(artObjects.dtosAtEntityList(page))
                remoteKeyDao.insertAll(keys)
            })
        }.flowOn(ioDispatcher)
    }

    internal fun removeAll(): Flow<Result<Unit>> {
        return flow {
            emit(runCatching {
                artObjectDao.removeAll()
                remoteKeyDao.removeAll()
            })
        }.flowOn(ioDispatcher)
    }

    internal fun getKeyByArtObjectId(id: String): Flow<Result<RemoteKeyEntity>> {
        return flow {
            emit(runCatching {
                remoteKeyDao.getByArtObjectId(id)
            })
        }.flowOn(ioDispatcher)
    }

    internal fun getCacheCreationTime(): Flow<Result<Long>> {
        return flow {
            emit(runCatching {
                remoteKeyDao.getCacheCreationDate()
            })
        }.flowOn(ioDispatcher)
    }
}