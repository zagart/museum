package com.zagart.museum.settings.presentation.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zagart.museum.core.ui.components.BaseTextField
import com.zagart.museum.core.ui.components.BaseTextFieldAction
import com.zagart.museum.core.ui.components.BaseToggle
import com.zagart.museum.core.ui.components.FailureScreen
import com.zagart.museum.core.ui.components.LoadingScreen
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.core.ui.icons.IconsProvider
import com.zagart.museum.settings.presentation.models.SettingsUiModel
import com.zagart.museum.settings.presentation.viewmodels.SettingsScreenState
import com.zagart.museum.settings.presentation.viewmodels.SettingsViewModel
import com.zagart.museum.shared.strings.StringProvider

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier, viewModel: SettingsViewModel
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
        state = settingsScreenState,
        onSettingsItemUpdate = { settingsItem ->
            viewModel.update(context, settingsItem)
        }
    )
}

@Composable
private fun SettingsScreen(
    modifier: Modifier,
    state: SettingsScreenState,
    onSettingsItemUpdate: (SettingsUiModel) -> Unit
) {
    when (state) {
        is SettingsScreenState.Failure -> FailureScreen()
        is SettingsScreenState.Loading -> LoadingScreen()
        is SettingsScreenState.Success -> {
            Surface {
                LazyColumn(
                    modifier
                        .fillMaxWidth()
                        .wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(DefaultSpacings.itemPadding),
                    contentPadding = PaddingValues(DefaultSpacings.itemPadding)
                ) {
                    items(state.settings) { settingsItem ->
                        if (settingsItem.firstLineKey == StringProvider.LANGUAGE) {
                            LanguageSelector(
                                state = state,
                                settingsItem = settingsItem,
                                onSettingsItemUpdate = onSettingsItemUpdate
                            )
                        } else {
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

@Composable
fun LanguageSelector(
    modifier: Modifier = Modifier,
    state: SettingsScreenState.Success,
    settingsItem: SettingsUiModel,
    onSettingsItemUpdate: (SettingsUiModel) -> Unit
) {
    BaseTextField(
        modifier = modifier,
        editable = false,
        nameValue = stringResource(id = settingsItem.firstLineRes),
        contentValue = stringResource(id = settingsItem.value),
        action = BaseTextFieldAction(
            normalIcon = IconsProvider.getArrowDown(),
            progressIcon = IconsProvider.getArrowUp()
        ) { actionModifier, actionResult ->
            val padding = 8.dp
            val itemHeight = 42.dp
            val containerHeight =
                itemHeight.times(state.settings.size) + padding * 3

            LazyColumn(
                modifier = actionModifier.height(containerHeight),
                verticalArrangement = Arrangement.spacedBy(padding),
                contentPadding = PaddingValues(padding)
            ) {
                items(state.languages) { languageRes ->
                    AssistChip(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusable(true)
                            .height(itemHeight),
                        onClick = {
                            actionResult()
                            onSettingsItemUpdate(settingsItem.copy(value = languageRes))
                        },
                        label = {
                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(id = languageRes)
                            )
                        }
                    )
                }
            }
        })
}