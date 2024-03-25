package com.zagart.museum.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun RemoteImage(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String,
    progressSize: Dp = 24.dp,
    cropImage: Boolean = true
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        CircularProgressIndicator(
            Modifier
                .size(progressSize)
                .align(Alignment.Center)
        )
        AsyncImage(
            modifier = Modifier,
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = if (cropImage) ContentScale.Crop else ContentScale.Fit
        )
    }
}