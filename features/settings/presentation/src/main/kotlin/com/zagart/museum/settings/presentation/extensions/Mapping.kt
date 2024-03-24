package com.zagart.museum.settings.presentation.extensions

import com.zagart.museum.settings.domain.models.SettingsItem
import com.zagart.museum.settings.presentation.models.SettingsUiModel

fun SettingsUiModel.asDomainModel(): SettingsItem {
    return SettingsItem(
        firstLineRes = firstLineRes,
        enabled = enabled
    )
}

fun SettingsItem.asUiModel(): SettingsUiModel {
    return SettingsUiModel(
        firstLineRes = firstLineRes,
        enabled = enabled
    )
}