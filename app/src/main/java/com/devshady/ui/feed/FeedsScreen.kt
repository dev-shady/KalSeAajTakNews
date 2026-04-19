package com.devshady.ui.feed


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun FeedScreen(onArticleClick: (Int) -> Unit) {
    Text("News Feed", color = Color.White)
}