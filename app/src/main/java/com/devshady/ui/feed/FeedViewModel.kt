package com.devshady.ui.feed

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshady.domain.GetFeedsUseCase
import com.devshady.domain.LoadNextPageUseCase
import com.devshady.domain.RefreshFeedsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FeedViewModel(
    private val refreshFeedsUseCase: RefreshFeedsUseCase,
    private val loadNextPageUseCase: LoadNextPageUseCase,
    getFeedsUseCase: GetFeedsUseCase
) : ViewModel() {

    init {
        refresh()
    }

    var lastRequestedPage = -1
    private var isLastPageReached = MutableStateFlow(false)

    private fun refresh() {
        viewModelScope.launch {
            refreshFeedsUseCase()
        }
    }

    val loadNextPage = { nextPage: Int ->
        if (nextPage > lastRequestedPage) {
            lastRequestedPage = nextPage
            viewModelScope.launch {
                val itemsCount = loadNextPageUseCase(nextPage)
                if (itemsCount == 0) {
                    isLastPageReached.value = true
                    feedsEvent.send(FeedsEvent.ShowNoMoreDataToast)
                }
            }
        }
    }

    val feedsUiState = getFeedsUseCase()
        .distinctUntilChanged()
        .combine(isLastPageReached) { feeds, isLastPage ->
            if (feeds.isEmpty() && !isLastPage) {
                FeedsUiState.Loading
            } else {
                FeedsUiState.Success(
                    feeds = feeds.map { it.toFeedsItem() },
                    isLastPageReached = isLastPage
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FeedsUiState.Loading
        )

    private val feedsEvent = Channel<FeedsEvent>()
    val feedsEventFlow = feedsEvent.receiveAsFlow()

    @Immutable
    data class FeedsItem(
        val id: String,
        val thumbnailUrl: String,
        val title: String,
        val description: String
    )

    sealed class FeedsUiState {
        object Loading : FeedsUiState()

        @Immutable
        data class Success(
            val feeds: List<FeedsItem>,
            val nextPage: Int = feeds.size / 10 + 1,
            val isLastPageReached: Boolean = false
        ) : FeedsUiState()

        data class Error(val errorMsg: String) : FeedsUiState()
    }

    sealed class FeedsEvent {
        object ShowNoMoreDataToast : FeedsEvent()
    }
}