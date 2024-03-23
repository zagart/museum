package com.zagart.museum.details.data.sources

import com.zagart.museum.api.MuseumApi
import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtObjectDetailsRemoteSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val museumApi: MuseumApi
) {

    internal fun getByObjectNumber(objectNumber: String): Flow<Result<ArtObjectDto>> {
        return flow {
            emit(runCatching {
                museumApi.requestArtObjectDetails(objectNumber = objectNumber).artObject
            })
        }.flowOn(ioDispatcher)
    }
}