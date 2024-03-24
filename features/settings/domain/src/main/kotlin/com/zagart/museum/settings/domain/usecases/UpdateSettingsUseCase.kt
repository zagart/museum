package com.zagart.museum.settings.domain.usecases

import com.zagart.museum.settings.domain.models.SettingsItem
import com.zagart.museum.settings.domain.repositories.SettingsRepository
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(item: SettingsItem) {
        return repository.update(item)
    }
}