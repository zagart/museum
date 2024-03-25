package com.zagart.museum.shared.strings

import androidx.annotation.StringRes

object StringProvider {

    const val DARK_THEME = "Dark theme"
    const val LANGUAGE = "Language"
    const val ENGLISH = "en"
    const val DUTCH = "nl"

    @StringRes
    fun getByKey(key: String): Int {
        return when (key) {
            DARK_THEME -> R.string.settings_item_dark_theme
            LANGUAGE -> R.string.settings_item_language
            ENGLISH -> R.string.language_en
            DUTCH -> R.string.language_nl
            else -> 0
        }
    }

    fun getByResource(@StringRes resource: Int): String {
        return when (resource) {
            R.string.settings_item_dark_theme -> DARK_THEME
            R.string.settings_item_language -> LANGUAGE
            R.string.language_en -> ENGLISH
            R.string.language_nl -> DUTCH
            else -> ""
        }
    }
}