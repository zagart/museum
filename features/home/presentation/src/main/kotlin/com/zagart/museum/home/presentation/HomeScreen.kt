package com.zagart.museum.home.presentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zagart.museum.core.ui.components.LoadingScreen
import com.zagart.museum.shared.strings.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    listState: LazyListState,
    onItemPressed: (String) -> Unit
) {
    val context = LocalContext.current
    val pagingItems = viewModel.artObjectsPagingData.collectAsLazyPagingItems()

    BackHandler {
        if (context is Activity) {
            context.finish()
        }
    }

    HomeScreen(pagingItems = pagingItems, onItemPressed = onItemPressed, listState = listState)
}

@Composable
private fun HomeScreen(
    listState: LazyListState,
    pagingItems: LazyPagingItems<HomeScreenItemModel>,
    onItemPressed: (String) -> Unit
) {
    if (pagingItems.loadState.refresh == LoadState.Loading) {
        LoadingScreen()
        return
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id },
            itemContent = { index ->
                val item = pagingItems[index]

                if (item != null) {
                    if (item.withAuthorHeader) {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = item.author,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    HomeScreenItem(item = item, onItemPressed = onItemPressed)
                }
            }
        )

        processPagingState(pagingItems.loadState.mediator, pagingItems)
    }
}

private fun LazyListScope.processPagingState(
    loadState: LoadStates?, pagingItems: LazyPagingItems<HomeScreenItemModel>
) {
    item {
        if (loadState?.append == LoadState.Loading) {
            AppendLoadingState()
        }

        if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
            val error = if (loadState.append is LoadState.Error) {
                (loadState.append as LoadState.Error).error
            } else {
                (loadState.refresh as LoadState.Error).error
            }


            val isPagingError = (loadState.append is LoadState.Error) || pagingItems.itemCount > 1

            val modifier = if (isPagingError) {
                Modifier.padding(8.dp)
            } else {
                Modifier.fillParentMaxSize()
            }

            ErrorState(modifier, isPagingError, error, pagingItems)
        }
    }
}

@Composable
private fun HomeScreenItem(
    modifier: Modifier = Modifier, item: HomeScreenItemModel, onItemPressed: (String) -> Unit
) {
    Surface(
        modifier = modifier.clip(MaterialTheme.shapes.small),
        shadowElevation = 12.dp
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .clickable { onItemPressed(item.objectNumber) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item.imageUrl?.let { imageUrl ->
                        //TODO: Reduce cache image quality
                        AsyncImage(
                            modifier = Modifier
                                .height(80.dp)
                                .width(110.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.primary)
                                .border(
                                    4.dp,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.shapes.small
                                )
                                .wrapContentWidth(),
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = item.title,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        modifier = Modifier.padding(top = 12.dp, end = 12.dp),
                        text = item.title,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun AppendLoadingState() {
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun ErrorState(
    modifier: Modifier,
    isPaginatingError: Boolean,
    error: Throwable,
    pagingItems: LazyPagingItems<HomeScreenItemModel>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (!isPaginatingError) {
            Icon(
                modifier = Modifier.size(64.dp),
                imageVector = Icons.Rounded.Warning,
                contentDescription = "Error icon",
                tint = MaterialTheme.colorScheme.error
            )
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = getErrorMessage(error),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error
            ),
            onClick = { pagingItems.refresh() },
            content = {
                Text(text = stringResource(id = R.string.button_name_refresh))
            })
    }
}

@Composable
private fun getErrorMessage(error: Throwable): String {
    return error.message ?: stringResource(id = R.string.text_failure)
}