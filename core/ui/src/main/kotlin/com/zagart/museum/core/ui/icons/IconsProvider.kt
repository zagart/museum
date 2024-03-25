package com.zagart.museum.core.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

object IconsProvider {

    fun getArrowDown(enabled: Boolean = true): ImageVector {
        return if (enabled) {
            Icons.Filled.ArrowDropDown
        } else {
            Icons.Outlined.ArrowDropDown
        }
    }

    fun getArrowUp(enabled: Boolean = true): ImageVector {
        return if (enabled) {
            Icons.Filled.ArrowDropUp
        } else {
            Icons.Outlined.ArrowDropUp
        }
    }

    fun getHomeIcon(enabled: Boolean = true): ImageVector {
        return if (enabled) {
            Icons.Filled.Home
        } else {
            Icons.Outlined.Home
        }
    }

    fun getMoreIcon(enabled: Boolean = true): ImageVector {
        return if (enabled) {
            Icons.Filled.MoreVert
        } else {
            Icons.Default.MoreVert
        }
    }

    fun getSettingsIcon(enabled: Boolean = true): ImageVector {
        return if (enabled) {
            Icons.Filled.Settings
        } else {
            Icons.Outlined.Settings
        }
    }
}