package com.devshady.ui.feed

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshady.domain.GetFeedsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FeedViewModel(getFeedsUseCase: GetFeedsUseCase): ViewModel() {

    @Immutable
    data class FeedsItem(
        val id: String,
        val thumbnailUrl: String,
        val title: String,
        val description: String
    )

    sealed class FeedsUiState {
        object Loading: FeedsUiState()
        @Immutable
        data class Success(val feeds: List<FeedsItem>): FeedsUiState()
        data class Error(val errorMsg: String): FeedsUiState()
    }

    val feedsUiState = getFeedsUseCase().distinctUntilChanged().map { list->
        FeedsUiState.Success(feeds = list.map { it.toFeedsItem() })

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FeedsUiState.Loading
    )
}