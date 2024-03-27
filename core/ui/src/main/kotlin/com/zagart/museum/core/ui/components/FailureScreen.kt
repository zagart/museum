package com.zagart.museum.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.shared.strings.R

@Preview
@Composable
fun FailureScreen(
    modifier: Modifier = Modifier,
    onButtonPressed: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            MessageText(
                modifier = Modifier,
                textColor = MaterialTheme.colorScheme.onErrorContainer,
                textRes = R.string.text_failure
            )
            Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onButtonPressed,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(text = stringResource(id = R.string.button_name_failure))
            }
        }
    }
}