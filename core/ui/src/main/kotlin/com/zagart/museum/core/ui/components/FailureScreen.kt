package com.zagart.museum.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zagart.museum.shared.strings.R

@Composable
fun FailureScreen(modifier: Modifier = Modifier) {
    MessageText(modifier, MaterialTheme.colorScheme.error, textRes = R.string.text_failure)
}