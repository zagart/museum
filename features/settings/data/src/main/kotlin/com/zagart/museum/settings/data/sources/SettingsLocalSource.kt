package com.zagart.museum.settings.data.sources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zagart.museum.core.data.di.DataStoreSettings
import com.zagart.museum.core.data.sources.DataStoreLocalSource
import com.zagart.museum.core.di.IoDispatcher
import com.zagart.museum.settings.data.extensions.asKeys
import com.zagart.museum.settings.data.models.SettingsItemEntity
import com.zagart.museum.shared.strings.StringKey
import com.zagart.museum.shared.strings.di.SettingsKeys
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsLocalSource @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    @DataStoreSettings dataStore: DataStore<Preferences>,
    @SettingsKeys settingsKeys: List<StringKey>
) : DataStoreLocalSource(
    ioDispatcher = ioDispatcher,
    dataStore = dataStore,
    keys = settingsKeys.asKeys()
) {

    fun getItems(): Flow<Result<List<SettingsItemEntity>>> {
        return dataStoreItems.map { dataStoreItems ->
            val settingsItems = mutableListOf<SettingsItemEntity>()

            dataStoreItems.booleans.forEach { boolean ->
                settingsItems.add(
                    SettingsItemEntity(
                        key = boolean.key.name,
                        enabled = boolean.value
                    )
                )
            }

            dataStoreItems.strings.forEach { string ->
                settingsItems.add(
                    SettingsItemEntity(
                        key = string.key.name,
                        value = string.value
                    )
                )
            }

            Result.success(settingsItems.toList())
        }.flowOn(ioDispatcher)
    }

    suspend fun update(item: SettingsItemEntity) {
        keys.find { key -> key.name == item.key }?.let {
            if (item.value.isNotBlank()) {
                updateItem(it, item.value)
            } else {
                updateItem(it, item.enabled)
            }
        }
    }

    fun getByKey(keyName: String): Flow<Result<SettingsItemEntity>> {
        return getStringItem(keyName).map { result ->
            result.map { value ->
                SettingsItemEntity(
                    key = keyName,
                    value = value
                )
            }
        }
    }
}