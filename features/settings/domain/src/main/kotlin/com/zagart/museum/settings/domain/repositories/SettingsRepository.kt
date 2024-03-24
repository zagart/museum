package com.zagart.museum.settings.domain.repositories

import com.zagart.museum.settings.domain.models.SettingsItem
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAll() : Flow<Result<List<SettingsItem>>>

    suspend fun update(item: SettingsItem)
}