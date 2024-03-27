package com.zagart.museum.core.ui.extentions

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun Modifier.disableTouchEvents(): Modifier {
    val interactionSource = remember { MutableInteractionSource() }

    return pointerInput(Unit) {
        awaitEachGesture {
            while (true) {
                awaitPointerEvent().changes.forEach { it.consume() }
            }
        }
    }.clickable(
        interactionSource = interactionSource,
        indication = null
    ) { }
}

fun Modifier.scrollable(): Modifier {
    return verticalScroll(ScrollState(0))
}