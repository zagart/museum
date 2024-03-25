package com.zagart.museum.settings.presentation.models

import androidx.annotation.StringRes

data class SettingsUiModel(
    @StringRes val firstLineRes: Int,
    val firstLineKey: String = "",
    @StringRes val value: Int = 0,
    val enabled: Boolean = false
)