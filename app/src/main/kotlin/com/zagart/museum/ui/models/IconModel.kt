package com.zagart.museum.ui.models

import androidx.compose.ui.graphics.vector.ImageVector

data class IconModel(
    val name: String,
    val activeIcon: ImageVector,
    val passiveIcon: ImageVector,
    val onItemPressed: () -> Unit,
    val enabled: Boolean = true
)