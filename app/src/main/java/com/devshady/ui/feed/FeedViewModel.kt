package com.devshady.ui.feed

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow

class FeedViewModel: ViewModel() {

    data class Feeds(
        val id: String,
        val thumbnailUrl: String,
        val title: String,
        val description: String
    )

    sealed class FeedsUiState {
        object Loading: FeedsUiState()
        data class Success(val feeds: List<Feeds>): FeedsUiState()
        data class Error(val errorMsg: String): FeedsUiState()
    }

    fun fetchNewsFeed() = flow<FeedsUiState> {
        emit(FeedsUiState.Loading)
    }

}