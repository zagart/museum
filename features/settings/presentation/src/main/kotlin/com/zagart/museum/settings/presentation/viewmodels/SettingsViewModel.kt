package com.zagart.museum.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zagart.museum.settings.domain.usecases.GetSettingsUseCase
import com.zagart.museum.settings.domain.usecases.UpdateSettingsUseCase
import com.zagart.museum.settings.presentation.extensions.asDomainModel
import com.zagart.museum.settings.presentation.extensions.asUiModel
import com.zagart.museum.settings.presentation.models.SettingsUiModel
import com.zagart.museum.shared.strings.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: GetSettingsUseCase,
    private val updateUseCase: UpdateSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SettingsScreenState>(SettingsScreenState.Loading)
    val state = _state.stateIn(
        scope = viewModelScope,
        initialValue = SettingsScreenState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    init {
        viewModelScope.launch {
            settingsUseCase().collectLatest { result ->
                if (result.isSuccess) {
                    val domainModels = result.getOrThrow()
                    val darkThemeModel =
                        domainModels.find { StringProvider.getByResource(it.firstLineRes) == StringProvider.DARK_THEME }

                    _state.value = SettingsScreenState.Success(
                        forceDarkTheme = darkThemeModel?.enabled ?: false,
                        settings = domainModels.map { domainModel ->
                            domainModel.asUiModel()
                        }
                    )
                } else {
                    _state.value = SettingsScreenState.Failure
                }
            }
        }
    }

    fun update(settingsUiModel: SettingsUiModel) {
        viewModelScope.launch { updateUseCase.invoke(settingsUiModel.asDomainModel()) }
    }
}

sealed interface SettingsScreenState {

    data object Loading : SettingsScreenState
    data object Failure : SettingsScreenState

    data class Success(
        val forceDarkTheme: Boolean = false,
        val settings: List<SettingsUiModel>
    ) : SettingsScreenState
}