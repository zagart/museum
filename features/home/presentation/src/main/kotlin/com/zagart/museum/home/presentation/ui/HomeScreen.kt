package com.zagart.museum.home.presentation.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zagart.museum.core.ui.components.FailureScreen
import com.zagart.museum.core.ui.components.LoadingScreen
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.core.ui.extentions.Toast
import com.zagart.museum.core.ui.extentions.scrollable
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
        },
        onRefresh = {
            viewModel.refresh()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeScreenState,
    listState: LazyListState,
    onItemPressed: (String) -> Unit,
    onScrollOverflow: (Int) -> Unit,
    onRefresh: () -> Unit
) {
    val refreshState = rememberPullToRefreshState()

    if (refreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
        }
    }

    when (state) {
        is HomeScreenState.Failure -> {
            FailureScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(),
                onButtonPressed = { onRefresh() }
            )
        }

        is HomeScreenState.Loading -> {
            LoadingScreen()
        }

        is HomeScreenState.Success -> {
            //default value of canScrollForward is false and it's not actual overflow
            var skipFirstOverflow by remember { mutableStateOf(true) }
            val canScrollForward by remember { derivedStateOf { !state.isAppendingFailed && listState.canScrollForward } }

            refreshState.endRefresh()

            if (!canScrollForward) {
                if (skipFirstOverflow) {
                    skipFirstOverflow = false
                } else {
                    if (!state.isAppendingFailed) {
                        AppendLoading()
                    }

                    LaunchedEffect(true) {
                        onScrollOverflow(state.items.size)
                    }
                }
            }

            if (state.isAppendingFailed) {
                Toast(id = R.string.text_loading_items_failure)
            }

            Box(Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
                if (state.items.isEmpty()) {
                    SkeletonLoading()
                } else {
                    HomeScreenContent(listState, state, onItemPressed)
                }
                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = refreshState,
                )
            }
        }
    }
}

@Composable
private fun HomeScreenContent(
    listState: LazyListState,
    state: HomeScreenState.Success,
    onItemPressed: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier,
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

fun imageWidthInDp(height: Dp, imageHeight: Int, imageWidth: Int): Dp {
    val maxFactor = 1.5f

    if (imageWidth.toFloat() / imageHeight.toFloat() > maxFactor) {
        return height.times(maxFactor)
    }

    val viewHeight = height.value
    val multiplier = imageHeight.toFloat() / viewHeight

    return Dp((imageWidth.toFloat() / multiplier))
}