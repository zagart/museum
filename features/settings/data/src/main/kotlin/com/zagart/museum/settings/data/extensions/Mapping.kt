package com.zagart.museum.settings.data.extensions

import com.zagart.museum.core.data.sources.Key
import com.zagart.museum.shared.strings.StringKey

fun List<StringKey>.asKeys(): List<Key> {
    return map { settingsKey ->
        Key(settingsKey.key, settingsKey.clazz)
    }
}