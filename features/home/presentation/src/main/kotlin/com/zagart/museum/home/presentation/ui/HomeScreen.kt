package com.zagart.museum.home.presentation.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zagart.museum.core.ui.components.FailureScreen
import com.zagart.museum.core.ui.components.LoadingScreen
import com.zagart.museum.core.ui.components.RemoteImage
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.home.presentation.models.HomeScreenImageUi
import com.zagart.museum.home.presentation.models.HomeScreenModelUi
import com.zagart.museum.home.presentation.viewmodels.HomeScreenState
import com.zagart.museum.home.presentation.viewmodels.HomeViewModel
import com.zagart.museum.shared.strings.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    listState: LazyListState,
    onItemPressed: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        if (context is Activity) {
            context.finish()
        }
        onBackPressed()
    }

    HomeScreen(
        state = state,
        listState = listState,
        onItemPressed = onItemPressed,
        onScrollOverflow = { size ->
            viewModel.loadMore(size)
        }
    )
}

//TODO: Provide interface for manual data refresh
@Composable
private fun HomeScreen(
    state: HomeScreenState,
    listState: LazyListState,
    onItemPressed: (String) -> Unit,
    onScrollOverflow: (Int) -> Unit
) {
    when (state) {
        is HomeScreenState.Failure -> FailureScreen()
        is HomeScreenState.Loading -> LoadingScreen()
        is HomeScreenState.Success -> {
            //default value of canScrollForward is false and it's not actual overflow
            var skipFirstOverflow by remember { mutableStateOf(true) }
            val canScrollForward by remember { derivedStateOf { !state.isAppendingFailed && listState.canScrollForward } }

            if (!canScrollForward) {
                if (skipFirstOverflow) {
                    skipFirstOverflow = false
                } else {
                    if (!state.isAppendingFailed) {
                        AppendLoadingState(state.items.isEmpty())
                    }
                    
                    LaunchedEffect(true) {
                        onScrollOverflow(state.items.size)
                    }
                }
            }

            if (state.isAppendingFailed) {
                val context = LocalContext.current
                val failureText = stringResource(id = R.string.text_loading_items_failure)

                LaunchedEffect(true) {
                    Toast.makeText(
                        context,
                        failureText,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = state.items, key = { it.id }) { item ->
                    if (item.withAuthorHeader) {
                        Text(
                            modifier = Modifier.padding(DefaultSpacings.itemPadding),
                            text = item.author,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HomeScreenItem(
                        item = item.copy(title = item.title),
                        onItemPressed = onItemPressed
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun HomeScreenItem(
    modifier: Modifier = Modifier,
    item: HomeScreenModelUi = HomeScreenModelUi(),
    onItemPressed: (String) -> Unit = {}
) {
    Surface(
        modifier = modifier.clip(MaterialTheme.shapes.small)
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .clickable { onItemPressed(item.objectNumber) }) {
            item.image?.let { image ->
                //TODO: Reduce cache image quality
                val imageHeightDp = 100.dp
                val imageWidthDp = imageWidthInDp(imageHeightDp, image.height, image.width)

                RemoteImage(
                    modifier = Modifier
                        .height(imageHeightDp)
                        .width(imageWidthDp)
                        .clip(MaterialTheme.shapes.small)
                        .border(
                            4.dp,
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.shapes.small
                        ),
                    height = imageHeightDp,
                    width = imageWidthDp,
                    imageUrl = image.url,
                    contentDescription = item.title
                )

                val innerPadding = 8.dp
                val textSectionHeight = imageHeightDp - innerPadding.times(2f)

                Column(
                    modifier = Modifier
                        .height(textSectionHeight)
                        .fillMaxWidth()
                        .padding(start = imageWidthDp, end = innerPadding)
                        .align(Alignment.Center)
                        .clip(
                            MaterialTheme.shapes.small.copy(
                                topStart = CornerSize(0.dp),
                                bottomStart = CornerSize(0.dp)
                            )
                        )
                        .background(MaterialTheme.colorScheme.secondary),
                ) {
                    val titleHeight = textSectionHeight.times(0.75f)

                    Text(
                        modifier = Modifier
                            .height(titleHeight)
                            .padding(DefaultSpacings.itemPadding),
                        text = item.title,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    item.productionPlaces?.let { productionPlaces ->
                        MetadataText(
                            modifier = Modifier
                                .height(textSectionHeight.times(0.25f))
                                .padding(horizontal = DefaultSpacings.itemPadding),
                            text = productionPlaces,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppendLoadingState(showPlaceholder: Boolean = false) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (showPlaceholder) {
            SkeletonLoading()
        }

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SkeletonLoading() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) { index ->
            HomeScreenItem(
                item = HomeScreenModelUi(
                    withAuthorHeader = index == 0,
                    author = stringResource(id = R.string.text_loading),
                    image = HomeScreenImageUi(
                        url = "",
                        height = 900,
                        width = 1600
                    )
                )
            )
        }
    }
}

fun imageWidthInDp(height: Dp, imageHeight: Int, imageWidth: Int): Dp {
    val maxFactor = 1.5f

    if (imageWidth.toFloat() / imageHeight.toFloat() > maxFactor) {
        return height.times(maxFactor)
    }

    val viewHeight = height.value
    val multiplier = imageHeight.toFloat() / viewHeight

    return Dp((imageWidth.toFloat() / multiplier))
}