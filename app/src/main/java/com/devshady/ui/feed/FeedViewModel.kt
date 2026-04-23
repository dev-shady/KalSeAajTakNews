package com.devshady.ui.feed

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshady.domain.GetFeedsUseCase
import com.devshady.domain.RefreshFeedsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeedsUseCase: GetFeedsUseCase,
    private val refreshFeedsUseCase: RefreshFeedsUseCase): ViewModel() {

    init {
        refresh()
    }

    private fun refresh() {
       viewModelScope.launch {
           refreshFeedsUseCase()
       }
    }

    val feedsUiState = getFeedsUseCase().distinctUntilChanged().map { list->
        FeedsUiState.Success(feeds = list.map { it.toFeedsItem() })

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FeedsUiState.Loading
    )

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
}