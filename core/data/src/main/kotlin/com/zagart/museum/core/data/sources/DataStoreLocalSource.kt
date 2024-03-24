package com.zagart.museum.core.data.sources

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

open class DataStoreLocalSource(
    context: Context,
    protected val ioDispatcher: CoroutineDispatcher,
    fileName: String,
    protected val keys: List<Key>
) {

    private val dataStore = PreferenceDataStoreFactory.create(produceFile = {
        context.preferencesDataStoreFile(fileName)
    })

    protected val dataStoreItems = dataStore.data.map {
        val preferences = it.toMutablePreferences()
        val booleans = mutableMapOf<Key, Boolean>()
        val strings = mutableMapOf<Key, String>()

        keys.forEach { key ->
            val value = preferences.getValue(key)

            if (value == true.toString() || value == false.toString()) {
                booleans[key] = value.toBooleanStrict()
            } else {
                strings[key] = value
            }
        }

        DataStoreItems(booleans, strings)
    }.flowOn(ioDispatcher)

    protected suspend fun updateItem(key: Key, enabled: Boolean) {
        dataStore.updateData { preferences ->
            val mutablePreferences = preferences.toMutablePreferences()
            mutablePreferences[booleanPreferencesKey(key.name)] = enabled

            mutablePreferences
        }
    }

    protected suspend fun updateItem(key: Key, value: String) {
        dataStore.updateData { preferences ->
            val mutablePreferences = preferences.toMutablePreferences()
            mutablePreferences[stringPreferencesKey(key.name)] = value

            mutablePreferences
        }
    }

    private fun MutablePreferences.getValue(key: Key): String {
        val value = this[getPreferencesKeyFor(key)]

        return value?.toString() ?: when (key.clazz) {
            Boolean::class -> false.toString()
            String::class -> ""
            else -> throw IllegalArgumentException("Unsupported type of preferences key")
        }
    }

    private fun getPreferencesKeyFor(key: Key): Preferences.Key<out Any> {
        return when (key.clazz) {
            Boolean::class -> booleanPreferencesKey(key.name)
            String::class -> stringPreferencesKey(key.name)
            else -> throw IllegalArgumentException("Unsupported type of preferences key")
        }
    }
}

data class Key(
    val name: String,
    val clazz: KClass<out Any>
)

data class DataStoreItems(
    val booleans: Map<Key, Boolean> = mutableMapOf(),
    val strings: Map<Key, String> = mutableMapOf()
)