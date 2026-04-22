package com.devshady.domain

import com.devshady.ui.feed.FeedViewModel

data class NewsPreview (
    val id: String,
    val thumbnailUrl: String,
    val title: String,
    val description: String
) {

    fun toFeedsItem() = FeedViewModel.FeedsItem(
        id,
        thumbnailUrl,
        title,
        description
    )
}