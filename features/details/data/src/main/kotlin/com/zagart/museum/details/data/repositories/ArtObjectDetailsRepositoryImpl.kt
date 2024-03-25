package com.zagart.museum.details.data.repositories

import com.zagart.museum.details.data.extensions.dtoAsDomainModel
import com.zagart.museum.details.data.extensions.entityAsDomainModel
import com.zagart.museum.details.data.sources.ArtObjectDetailsLocalSource
import com.zagart.museum.details.data.sources.ArtObjectDetailsRemoteSource
import com.zagart.museum.domain.repositories.ArtObjectDetailsRepository
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.settings.domain.repositories.SettingsRepository
import com.zagart.museum.shared.strings.StringProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class ArtObjectDetailsRepositoryImpl @Inject constructor(
    private val localSource: ArtObjectDetailsLocalSource,
    private val remoteSource: ArtObjectDetailsRemoteSource,
    private val settingsRepository: SettingsRepository
) : ArtObjectDetailsRepository {

    override suspend fun getByObjectNumber(objectNumber: String): Flow<Result<ArtObject>> {
        val cacheFlow = localSource.getByObjectNumber(objectNumber)
        val networkFlow = remoteSource.getByObjectNumber(objectNumber)
        val languageFlow = settingsRepository.getByKey(StringProvider.LANGUAGE).map { result ->
            result.map { settingsItem ->
                settingsItem.value == StringProvider.ENGLISH
            }
        }.map { result ->
            result.getOrNull() == true
        }

        val result = cacheFlow.single()

        return if (result.isSuccess) {
            cacheFlow.map { cacheResult -> cacheResult.map { entity -> entity.entityAsDomainModel() } }
        } else {
            networkFlow.combine(languageFlow) { networkResult, useEnglish ->
                return@combine if (networkResult.isSuccess) {
                    networkResult.map { dto ->
                        localSource.upsert(dto)
                        dto.dtoAsDomainModel(useEnglish)
                    }
                } else {
                    Result.failure(
                        networkResult.exceptionOrNull()
                            ?: Throwable("Failure during details loading for $objectNumber")
                    )
                }
            }
        }
    }
}
