package com.zagart.museum.settings.data.extensions

import com.zagart.museum.core.data.sources.Key
import com.zagart.museum.settings.data.models.SettingsItemEntity
import com.zagart.museum.settings.domain.models.SettingsItem
import com.zagart.museum.shared.strings.StringKey
import com.zagart.museum.shared.strings.StringProvider

fun List<StringKey>.asKeys(): List<Key> {
    return map { settingsKey ->
        Key(settingsKey.key, settingsKey.clazz, settingsKey.default)
    }
}

fun SettingsItemEntity.asDomainModel(): SettingsItem {
    return SettingsItem(
        StringProvider.getByKey(key),
        enabled,
        value
    )
}