package com.zagart.museum.home.data.sources

import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.home.data.models.ArtObjectEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtObjectLocalSource @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getArtObjects(): Flow<Result<List<ArtObjectEntity>>> {
        return flow<Result<List<ArtObjectEntity>>> {
            //TODO: Stub, add caching
            emit(Result.success(emptyList()))
        }.flowOn(ioDispatcher)
    }
}
