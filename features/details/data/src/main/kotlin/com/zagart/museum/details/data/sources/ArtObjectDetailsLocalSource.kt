package com.zagart.museum.details.data.sources

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.details.data.daos.ArtObjectDetailsDao
import com.zagart.museum.details.data.extensions.dtoAsEntityModel
import com.zagart.museum.details.data.models.ArtObjectDetailsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtObjectDetailsLocalSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val artObjectDetailsDao: ArtObjectDetailsDao
) {

    internal fun getByObjectNumber(objectNumber: String): Flow<Result<ArtObjectDetailsEntity>> {
        return flow {
            emit(runCatching {
                artObjectDetailsDao.getByObjectNumber(objectNumber)
                    ?: throw IllegalStateException("Entity with object number $objectNumber are missing")
            })
        }.flowOn(ioDispatcher)
    }

    fun upsert(dto: ArtObjectDto) {
        flow {
            emit(runCatching { artObjectDetailsDao.upsert(dto.dtoAsEntityModel()) })
        }.flowOn(ioDispatcher)
    }
}
