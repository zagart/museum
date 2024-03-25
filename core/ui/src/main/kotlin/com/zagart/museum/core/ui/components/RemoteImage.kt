package com.zagart.museum.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun RemoteImage(
    modifier: Modifier,
    height: Dp,
    width: Dp,
    imageUrl: String,
    contentDescription: String,
    progressSize: Dp = 24.dp
) {
    var showLoading by rememberSaveable { mutableStateOf(true) }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        if (showLoading) {
            CircularProgressIndicator(
                Modifier
                    .size(progressSize)
                    .align(Alignment.Center)
            )
        }

        val widthPx = with(LocalDensity.current) {
            width.toPx().toInt()
        }
        val heightPx = with(LocalDensity.current) {
            height.toPx().toInt()
        }
        AsyncImage(
            modifier = Modifier,
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .size(widthPx, heightPx)
                .listener { _, _ ->
                    showLoading = false
                }
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}