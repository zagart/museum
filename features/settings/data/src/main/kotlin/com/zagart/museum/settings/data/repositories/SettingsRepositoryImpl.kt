package com.zagart.museum.settings.data.repositories

import com.zagart.museum.settings.data.extensions.asDomainModel
import com.zagart.museum.settings.data.models.SettingsItemEntity
import com.zagart.museum.settings.data.sources.SettingsLocalSource
import com.zagart.museum.settings.domain.models.SettingsItem
import com.zagart.museum.settings.domain.repositories.SettingsRepository
import com.zagart.museum.shared.strings.StringProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val localSource: SettingsLocalSource
) : SettingsRepository {

    override fun getAll(): Flow<Result<List<SettingsItem>>> {
        return localSource.getItems().map { result ->
            result.map { entityList ->
                entityList.map { entity ->
                    entity.asDomainModel()
                }
            }
        }
    }

    override fun getByKey(key: String): Flow<Result<SettingsItem>> {
        return localSource.getByKey(key).map { result ->
            result.map { entity ->
                entity.asDomainModel()
            }
        }
    }

    override suspend fun update(item: SettingsItem) {
        localSource.update(
            SettingsItemEntity(
                StringProvider.getByResource(item.firstLineRes),
                item.enabled,
                item.value
            )
        )
    }
}