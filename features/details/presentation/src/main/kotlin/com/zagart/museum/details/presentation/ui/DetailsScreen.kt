package com.zagart.museum.details.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zagart.museum.core.ui.components.FailureScreen
import com.zagart.museum.core.ui.components.LoadingScreen
import com.zagart.museum.core.ui.components.RemoteImage
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.core.ui.theme.MuseumTheme
import com.zagart.museum.details.presentation.models.DetailsUiAuthor
import com.zagart.museum.details.presentation.models.DetailsUiModel
import com.zagart.museum.details.presentation.viewmodels.DetailsScreenState
import com.zagart.museum.details.presentation.viewmodels.DetailsViewModel
import com.zagart.museum.home.presentation.ui.MetadataText

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, onBackPressed: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        onBackPressed()
    }
    DetailsScreen(state = state)
}

@Preview
@Composable
fun DetailsScreenPreview() {
    MuseumTheme {
        DetailsScreen(
            state = DetailsScreenState.Success(
                details = DetailsUiModel(
                    title = "Details Page Title",
                    description = "Description of the the item",
                    authors = listOf(
                        DetailsUiAuthor(
                            name = "Test Author Name",
                            qualification = "possibly"
                        )
                    )
                )
            )
        )
    }
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
            val itemModifier = Modifier.padding(
                start = DefaultSpacings.itemPadding,
                end = DefaultSpacings.itemPadding
            )

            Box {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .verticalScroll(rememberScrollState(0))
                ) {
                    Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))

                    Text(
                        modifier = itemModifier.fillMaxWidth(),
                        text = state.details.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))

                    state.details.image?.let { image ->
                        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                        val imageWidth = screenWidth - DefaultSpacings.itemPadding.times(2)
                        val imageHeight =
                            imageWidth.times(image.height.toFloat() / image.width.toFloat())

                        RemoteImage(
                            modifier = itemModifier
                                .width(imageWidth)
                                .height(imageHeight)
                                .clip(MaterialTheme.shapes.small),
                            imageUrl = image.url,
                            contentDescription = state.details.title,
                            progressSize = 64.dp,
                            width = imageWidth,
                            height = imageHeight
                        )
                        Spacer(
                            modifier = Modifier.height(
                                DefaultSpacings.itemPadding.minus(
                                    if (state.details.metadata == null) {
                                        0.dp
                                    } else {
                                        DefaultSpacings.itemPadding.div(2)
                                    }
                                )
                            )
                        )
                    }

                    state.details.metadata?.let { metadata ->
                        MetadataText(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(horizontal = DefaultSpacings.itemPadding),
                            text = metadata,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))
                    }

                    state.details.description.takeIf { it.isNotBlank() }?.let { description ->
                        Text(
                            modifier = itemModifier,
                            text = description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))
                    }

                    state.details.authors?.takeIf { it.isNotEmpty() }?.let { authors ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = DefaultSpacings.itemPadding)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding.div(2)))
                            MetadataText(
                                modifier = Modifier.padding(
                                    horizontal = DefaultSpacings.itemPadding.div(2)
                                ),
                                text = "Authors",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))
                            authors.forEach { author ->
                                AuthorSection(
                                    modifier = Modifier.padding(horizontal = DefaultSpacings.itemPadding),
                                    author = author
                                )
                                Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))
                            }
                        }
                        Spacer(modifier = Modifier.height(DefaultSpacings.itemPadding))
                    }
                }
            }
        }
    }
}

