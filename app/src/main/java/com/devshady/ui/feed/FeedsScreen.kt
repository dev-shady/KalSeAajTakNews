package com.devshady.ui.feed


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devshady.ui.feed.FeedViewModel.FeedsUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun FeedScreen(
    uiState: FeedsUiState,
    feedsEvents: Flow<FeedViewModel.FeedsEvent>,
    onArticleClick: (String) -> Unit,
    loadNextPage: (Int) -> Unit
) {

    val lazyListState = rememberLazyListState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index > lazyListState.layoutInfo.totalItemsCount - 5 // if less than 6 items are remaining, load more
        }
    }

    val context = LocalContext.current

    LaunchedEffect(shouldLoadMore.value) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged() // Only emits when it flips from false -> true
            .filter { it }   // Only proceed if the value is true
            .collect {
                loadNextPage(lazyListState.layoutInfo.totalItemsCount / 10 + 1)
            }
    }

    LaunchedEffect(Unit) {
        feedsEvents.collect { event ->
            when (event) {
                FeedViewModel.FeedsEvent.ShowNoMoreDataToast ->
                    Toast.makeText(context, "No More Data Available", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
    when (uiState) {
        is FeedsUiState.Success -> {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = uiState.feeds,
                    key = { it.id } // Senior Tip: Always provide a key for performance!
                ) { article ->
                    NewsItemRow(article, onArticleClick)

                }
                if (shouldLoadMore.value && !uiState.isLastPageReached) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                }
            }

        }

        is FeedsUiState.Loading -> {
            FeedsShimmer()
        }

        else -> {}
    }


}