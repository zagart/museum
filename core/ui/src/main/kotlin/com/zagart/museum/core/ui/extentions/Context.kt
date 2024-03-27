package com.zagart.museum.core.ui.extentions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun Toast(@StringRes id : Int) {
    val context = LocalContext.current
    val failureText = stringResource(id = id)

    LaunchedEffect(true) {
        Toast.makeText(
            context,
            failureText,
            Toast.LENGTH_SHORT
        ).show()
    }
}