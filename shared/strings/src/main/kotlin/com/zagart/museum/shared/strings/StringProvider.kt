package com.zagart.museum.shared.strings

import androidx.annotation.StringRes

object StringProvider {

    const val DARK_THEME = "Dark theme"

    @StringRes
    fun getByKey(key: String): Int {
        return when (key) {
            DARK_THEME -> R.string.settings_item_dark_theme
            else -> throw IllegalArgumentException("String key not found")
        }
    }

    fun getByResource(@StringRes resource: Int): String {
        return when (resource) {
            R.string.settings_item_dark_theme -> DARK_THEME
            else -> throw IllegalArgumentException("String resource not found")
        }
    }
}