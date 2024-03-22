package com.zagart.museum.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel, onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        onBackPressed()
    }
    HomeScreen(state = state)
}

@Composable
private fun HomeScreen(state: HomeScreenState) {
    when (state) {
        is HomeScreenState.Failure -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Failure"
                )
            }
        }

        is HomeScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Loading..."
                )
            }
        }

        is HomeScreenState.Success -> {
            LazyColumn {
                items(state.items) {
                    Box(
                        modifier = Modifier
                            .heightIn(min = 40.dp)
                            .padding(12.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = it.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}