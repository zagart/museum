package com.zagart.museum.settings.presentation.models

import androidx.annotation.StringRes

data class SettingsUiModel(
    @StringRes val firstLineRes : Int,
    val enabled : Boolean
)