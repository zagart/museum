package com.zagart.museum.details.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zagart.museum.core.ui.components.FailureScreen
import com.zagart.museum.core.ui.components.LoadingScreen
import com.zagart.museum.core.ui.components.RemoteImage
import com.zagart.museum.core.ui.configs.DefaultSpacings

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

                    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    val screenWidthMinusPadding = screenWidth - DefaultSpacings.itemPadding.times(2)

                    state.details.image?.let { image ->
                        RemoteImage(
                            modifier = itemModifier
                                .width(screenWidthMinusPadding)
                                .height(screenWidthMinusPadding.times(image.height.toFloat() / image.width.toFloat()))
                                .clip(MaterialTheme.shapes.small),
                            imageUrl = image.url,
                            contentDescription = state.details.title,
                            progressSize = 64.dp,
                            cropImage = false
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
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
