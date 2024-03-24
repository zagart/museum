package com.zagart.museum.details.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zagart.museum.core.ui.components.FailureScreen
import com.zagart.museum.core.ui.components.LoadingScreen

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
        is DetailsScreenState.Failure -> {
            FailureScreen()
        }

        is DetailsScreenState.Loading -> {
            LoadingScreen()
        }

        is DetailsScreenState.Success -> {
            val itemModifier = Modifier.padding(start = 12.dp, end = 12.dp)

            Box {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .verticalScroll(rememberScrollState(0))
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = itemModifier,
                        text = state.details.author,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = itemModifier,
                        text = state.details.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AsyncImage(
                        modifier = itemModifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(MaterialTheme.shapes.small),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(state.details.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = state.details.title
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = itemModifier,
                        text = state.details.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}