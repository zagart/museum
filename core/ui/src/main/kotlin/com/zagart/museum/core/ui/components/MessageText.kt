package com.zagart.museum.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun MessageText(
    modifier: Modifier = Modifier,
    textColor: Color,
    @StringRes textRes: Int
) {
    Text(
        modifier = modifier
            .padding(8.dp)
            .wrapContentSize(),
        text = stringResource(id = textRes),
        textAlign = TextAlign.Center,
        color = textColor,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis
    )
}