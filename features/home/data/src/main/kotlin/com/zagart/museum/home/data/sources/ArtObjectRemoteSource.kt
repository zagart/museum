package com.zagart.museum.home.data.sources

import com.zagart.museum.api.MuseumApi
import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtObjectRemoteSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher, private val api: MuseumApi
) {

    internal fun getByPage(page: Int, pageSize: Int): Flow<Result<List<ArtObjectDto>>> {
        return flow {
            emit(runCatching {
                api.requestArtObjects(page = page, pageSize = pageSize).artObjects
            })
        }.flowOn(ioDispatcher)
    }
}
