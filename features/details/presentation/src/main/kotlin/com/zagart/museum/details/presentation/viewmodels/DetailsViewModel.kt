package com.zagart.museum.details.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zagart.museum.details.presentation.extensions.domainAsUiModel
import com.zagart.museum.details.presentation.models.DetailsUiModel
import com.zagart.museum.domain.usecases.GetArtObjectDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: GetArtObjectDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Loading)
    val state = _state.stateIn(
        scope = viewModelScope,
        initialValue = DetailsScreenState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    private var objectNumber = ""

    fun prepare(objectNumber: String) {
        this.objectNumber = objectNumber
        update()
    }

    fun update(minResultTime: Long? = null) {
        if (_state.value !is DetailsScreenState.Loading) {
            _state.value = DetailsScreenState.Loading
        }

        viewModelScope.launch {
            val delayDef: Deferred<Unit>? =
                if (minResultTime == null || minResultTime <= 0L) null else async {
                    delay(
                        minResultTime
                    )
                }

            delayDef?.start()
            detailsUseCase(objectNumber).collectLatest { detailsResult ->
                delayDef?.await()

                if (detailsResult.isSuccess) {
                    _state.value = DetailsScreenState.Success(
                        details = detailsResult.getOrThrow().domainAsUiModel()
                    )
                } else {
                    _state.value = DetailsScreenState.Failure
                }
            }
        }
    }
}

sealed interface DetailsScreenState {

    data object Loading : DetailsScreenState

    data object Failure : DetailsScreenState

    data class Success(
        val details: DetailsUiModel
    ) : DetailsScreenState
}