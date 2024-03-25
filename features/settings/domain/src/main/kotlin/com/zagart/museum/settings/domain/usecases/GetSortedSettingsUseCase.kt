package com.zagart.museum.settings.domain.usecases

import com.zagart.museum.settings.domain.models.SettingsItem
import com.zagart.museum.settings.domain.repositories.SettingsRepository
import com.zagart.museum.shared.strings.StringProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<Result<List<SettingsItem>>> {
        return repository.getAll().map { result ->
            result.map { settings ->
                settings.sortedByDescending { item ->
                    StringProvider.getByResource(item.firstLineRes)
                }
            }
        }
    }
}