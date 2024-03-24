package com.zagart.museum.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zagart.museum.core.ui.configs.DefaultSpacings

@Composable
fun BaseToggle(
    @StringRes
    firstLineRes: Int,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(DefaultSpacings.itemPadding)
    ) {
        Spacer(modifier = Modifier.width(DefaultSpacings.itemPadding))
        Text(
            modifier = Modifier
                .weight(0.75f)
                .align(Alignment.CenterVertically),
            text = stringResource(firstLineRes),
            style = MaterialTheme.typography.titleMedium
        )
        Switch(
            modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically),
            checked = enabled,
            onCheckedChange = { enabled ->
                onToggle(enabled)
            }
        )
    }
}