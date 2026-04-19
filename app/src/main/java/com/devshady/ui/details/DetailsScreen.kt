package com.devshady.ui.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DetailsScreen(articleId: Int) {
    Text("News Details id: ${articleId}", color = Color.White)
}