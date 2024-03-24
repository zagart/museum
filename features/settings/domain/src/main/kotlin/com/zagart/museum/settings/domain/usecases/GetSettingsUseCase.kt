package com.zagart.museum.settings.domain.usecases

import com.zagart.museum.settings.domain.models.SettingsItem
import com.zagart.museum.settings.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository : SettingsRepository
) {

    operator fun invoke() : Flow<Result<List<SettingsItem>>> {
        return repository.getAll()
    }
}