package com.devshady.ui.feed


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devshady.ui.feed.FeedViewModel.FeedsUiState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun FeedScreen(uiState: FeedsUiState, onArticleClick: (String) -> Unit) {

        when(uiState) {
            is FeedsUiState.Success -> {
                LazyColumn(
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
                }

            }
            is FeedsUiState.Loading -> {
                Text("Loading...", color = Color.White)
            }
            else -> {}
        }



}