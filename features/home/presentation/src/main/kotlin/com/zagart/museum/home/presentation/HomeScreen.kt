package com.zagart.museum.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

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
            val pagingItems = state.artObjectsPagingData.collectAsLazyPagingItems()

            LazyColumn {
                items(
                    count = pagingItems.itemCount,
                    key = { index -> pagingItems[index]?.id ?: index },
                    itemContent = { index ->
                        HomeScreenItem(item = pagingItems[index])
                    }
                )

                processPagingState(pagingItems.loadState.mediator, pagingItems)
            }
        }
    }
}

private fun LazyListScope.processPagingState(
    loadState: LoadStates?,
    pagingItems: LazyPagingItems<HomeScreenItemModel>
) {
    item {
        if (loadState?.refresh == LoadState.Loading) {
            RefreshLoadingState()
        }

        if (loadState?.append == LoadState.Loading) {
            AppendLoadingState()
        }

        if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
            val error = if (loadState.append is LoadState.Error) {
                (loadState.append as LoadState.Error).error
            } else {
                (loadState.refresh as LoadState.Error).error
            }


            val isPagingError =
                (loadState.append is LoadState.Error) || pagingItems.itemCount > 1

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
    modifier: Modifier = Modifier,
    item: HomeScreenItemModel?
) {
    if (item != null) {
        Box(
            modifier = modifier
                .heightIn(min = 40.dp)
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = item.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun RefreshLoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Refresh Loading"
        )

        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun AppendLoadingState() {
    Box(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
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
                imageVector = Icons.Rounded.Warning, contentDescription = null
            )
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = error.message ?: error.toString(),
            textAlign = TextAlign.Center,
        )

        Button(
            onClick = { pagingItems.refresh() },
            content = {
                Text(text = "Refresh")
            }
        )
    }
}