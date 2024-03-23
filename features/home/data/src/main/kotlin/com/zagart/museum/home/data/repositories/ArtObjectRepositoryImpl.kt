package com.zagart.museum.home.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.zagart.museum.api.MuseumApi
import com.zagart.museum.api.PAGE_SIZE
import com.zagart.museum.home.data.extensions.asRemoteKeyEntity
import com.zagart.museum.home.data.extensions.toDomainModel
import com.zagart.museum.home.data.models.ArtObjectShortEntity
import com.zagart.museum.home.data.models.RemoteKeyEntity
import com.zagart.museum.home.data.sources.ArtObjectLocalSource
import com.zagart.museum.home.data.sources.ArtObjectRemoteSource
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject


//TODO: Transaction for ArtObjectEntity + RemoteKeyEntity (add Foreign Key)
class ArtObjectRepositoryImpl @Inject constructor(
    private val localSource: ArtObjectLocalSource,
    private val remoteSource: ArtObjectRemoteSource
) : ArtObjectRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArtObjectsPagingData(): Flow<PagingData<ArtObject>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { localSource.getAll() },
            remoteMediator = ArtObjectsMediator(localSource, remoteSource)
        ).flow.map { pagingData -> pagingData.map { entity -> entity.toDomainModel() } }
    }

    @OptIn(ExperimentalPagingApi::class)
    private class ArtObjectsMediator(
        private val localSource: ArtObjectLocalSource,
        private val remoteSource: ArtObjectRemoteSource
    ) : RemoteMediator<Int, ArtObjectShortEntity>() {

        private val defaultMediatorException = Throwable("Failure in ArtObjectsMediator#load")

        override suspend fun initialize(): InitializeAction {
            val cacheCreationTimeResult = localSource.getCacheCreationTime().single()

            return if (cacheCreationTimeResult.isSuccess && (System.currentTimeMillis() - cacheCreationTimeResult.getOrThrow()) < MuseumApi.CACHE_EXPIRE_TIME) {
                InitializeAction.SKIP_INITIAL_REFRESH
            } else {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            }
        }

        override suspend fun load(
            loadType: LoadType, state: PagingState<Int, ArtObjectShortEntity>
        ): MediatorResult {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    getClosestKey(state)?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    getFirstKey(state).let { key ->
                        key?.previousPage ?: return MediatorResult.Success(key != null)
                    }
                }

                LoadType.APPEND -> {
                    getLastKey(state).let { key ->
                        key?.nextPage ?: return MediatorResult.Success(key != null)
                    }
                }
            }

            val loadingResult = runCatching<Boolean> {
                val pageItemsResult = remoteSource.getByPage(page).single()

                if (pageItemsResult.isSuccess) {
                    val dtos = pageItemsResult.getOrThrow()
                    val stopPagination = dtos.isEmpty()

                    if (loadType == LoadType.REFRESH) {
                        localSource.removeAll()
                    }

                    val insertionResult = localSource.insertPage(page, dtos, dtos.map { dto ->
                        dto.asRemoteKeyEntity(page, stopPagination)
                    }).single()

                    if (insertionResult.isFailure) {
                        //Since cache is SOT, we can't proceed with DTOs only
                        return MediatorResult.Error(
                            insertionResult.exceptionOrNull() ?: defaultMediatorException
                        )
                    }

                    return@runCatching stopPagination
                } else {
                    return MediatorResult.Error(
                        pageItemsResult.exceptionOrNull() ?: defaultMediatorException
                    )
                }
            }

            return if (loadingResult.isSuccess) {
                MediatorResult.Success(endOfPaginationReached = loadingResult.getOrThrow())
            } else {
                MediatorResult.Error(loadingResult.exceptionOrNull() ?: defaultMediatorException)
            }
        }

        private suspend fun getClosestKey(state: PagingState<Int, ArtObjectShortEntity>): RemoteKeyEntity? {
            return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    localSource.getKeyByArtObjectId(id).single().getOrNull()
                }
            }
        }

        private suspend fun getFirstKey(state: PagingState<Int, ArtObjectShortEntity>): RemoteKeyEntity? {
            return state.pages.firstOrNull {
                it.data.isNotEmpty()
            }?.data?.firstOrNull()?.let { artObject ->
                localSource.getKeyByArtObjectId(artObject.id).single().getOrNull()
            }
        }

        private suspend fun getLastKey(state: PagingState<Int, ArtObjectShortEntity>): RemoteKeyEntity? {
            return state.pages.lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()?.let { artObject ->
                localSource.getKeyByArtObjectId(artObject.id).single().getOrNull()
            }
        }
    }
}