package com.zagart.museum.ui.animations

import androidx.compose.animation.animateContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

private const val ZOOM_SCALE = 1.15f

@Composable
fun Modifier.applyZoomIn(applyZoom: Boolean): Modifier {
    return this
        .scale(if (applyZoom) ZOOM_SCALE else 1f)
        .animateContentSize()
}