package com.zagart.museum.settings.domain.models

import androidx.annotation.StringRes

data class SettingsItem(
    @StringRes val firstLineRes : Int,
    val enabled : Boolean
)