package com.zagart.museum.core.ui.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.zagart.museum.core.ui.icons.IconsProvider

data class IconModel(
    val name: String,
    val activeIcon: ImageVector,
    val passiveIcon: ImageVector,
    val onItemPressed: () -> Unit = {},
    val enabled: Boolean = true
) {

    companion object {

        val BACK = IconModel(
            name = "Back",
            activeIcon = IconsProvider.getBackIcon(true),
            passiveIcon = IconsProvider.getBackIcon(false)
        )
    }
}