package com.zagart.museum.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zagart.museum.core.ui.models.IconModel

private const val DISABLED_ICON_ALPHA = 0.3f

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    leftAction: IconModel? = null,
    rightAction: IconModel? = null
) {
    val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val onBackgroundColor = MaterialTheme.colorScheme.onSecondaryContainer

    Surface(modifier = modifier.shadow(12.dp)) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 64.dp)
                .background(backgroundColor)
        ) {
            if (leftAction != null) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(64.dp)
                        .alpha(if (leftAction.enabled) 1f else DISABLED_ICON_ALPHA),
                    onClick = leftAction.onItemPressed,
                    enabled = leftAction.enabled
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = leftAction.activeIcon),
                        tint = onBackgroundColor,
                        contentDescription = leftAction.name
                    )
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize()
                    .padding(
                        start = 64.dp,
                        end = 64.dp,
                    ),
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(color = onBackgroundColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (rightAction != null) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(64.dp)
                        .alpha(if (rightAction.enabled) 1f else DISABLED_ICON_ALPHA),
                    onClick = rightAction.onItemPressed,
                    enabled = rightAction.enabled
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = rightAction.activeIcon),
                        tint = onBackgroundColor,
                        contentDescription = rightAction.name
                    )
                }
            }
        }
    }
}