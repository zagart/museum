package com.zagart.museum.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zagart.museum.shared.strings.R

@Composable
fun FailureScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        MessageText(
            Modifier.align(Alignment.Center),
            MaterialTheme.colorScheme.error,
            textRes = R.string.text_failure
        )
    }
}