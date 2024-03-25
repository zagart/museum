package com.zagart.museum.settings.presentation.extensions

import com.zagart.museum.settings.domain.models.SettingsItem
import com.zagart.museum.settings.presentation.models.SettingsUiModel
import com.zagart.museum.shared.strings.StringProvider

fun SettingsUiModel.asDomainModel(): SettingsItem {
    return SettingsItem(
        firstLineRes = firstLineRes,
        enabled = enabled,
        value = StringProvider.getByResource(value)
    )
}

fun SettingsItem.asUiModel(): SettingsUiModel {
    return SettingsUiModel(
        firstLineRes = firstLineRes,
        firstLineKey = StringProvider.getByResource(firstLineRes),
        enabled = enabled,
        value = StringProvider.getByKey(value)
    )
}