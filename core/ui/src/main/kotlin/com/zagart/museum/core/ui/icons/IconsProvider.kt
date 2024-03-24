package com.zagart.museum.core.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

object IconsProvider {

    fun getHomeIcon(enabled: Boolean): ImageVector {
        return if (enabled) {
            Icons.Filled.Home
        } else {
            Icons.Outlined.Home
        }
    }

    fun getSettingsIcon(enabled: Boolean): ImageVector {
        return if (enabled) {
            Icons.Filled.Settings
        } else {
            Icons.Outlined.Settings
        }
    }
}