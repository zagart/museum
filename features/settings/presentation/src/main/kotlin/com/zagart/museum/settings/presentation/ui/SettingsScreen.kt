package com.zagart.museum.settings.presentation.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zagart.museum.core.ui.components.BaseToggle
import com.zagart.museum.core.ui.components.FailureScreen
import com.zagart.museum.core.ui.components.LoadingScreen
import com.zagart.museum.settings.presentation.models.SettingsUiModel
import com.zagart.museum.settings.presentation.viewmodels.SettingsScreenState
import com.zagart.museum.settings.presentation.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val settingsScreenState by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        if (context is Activity) {
            context.finish()
        }
    }
    SettingsScreen(
        modifier = modifier,
        settingsScreenState = settingsScreenState,
        onSettingsItemUpdate = { settingsItem ->
            viewModel.update(settingsItem)
        }
    )
}

@Composable
private fun SettingsScreen(
    modifier: Modifier,
    settingsScreenState: SettingsScreenState,
    onSettingsItemUpdate: (SettingsUiModel) -> Unit
) {
    when (settingsScreenState) {
        is SettingsScreenState.Failure -> FailureScreen()
        is SettingsScreenState.Loading -> LoadingScreen()
        is SettingsScreenState.Success -> {
            Surface {
                Column(
                    modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                ) {
                    LazyColumn {
                        items(settingsScreenState.settings) { settingsItem ->
                            BaseToggle(
                                firstLineRes = settingsItem.firstLineRes,
                                enabled = settingsItem.enabled,
                                onToggle = { enabled ->
                                    onSettingsItemUpdate(settingsItem.copy(enabled = enabled))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}