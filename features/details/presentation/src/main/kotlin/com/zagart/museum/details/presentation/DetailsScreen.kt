package com.zagart.museum.details.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, onBackPressed: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        onBackPressed()
    }
    DetailsScreen(state = state)
}

@Composable
private fun DetailsScreen(state: DetailsScreenState) {
    when (state) {
        is DetailsScreenState.Failure -> {}
        is DetailsScreenState.Loading -> {}
        is DetailsScreenState.Success -> {
            Box {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(text = state.details.title)
                    Text(text = state.details.description)
                }
            }
        }
    }
}