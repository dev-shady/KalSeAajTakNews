package com.devshady.domain

import kotlinx.coroutines.flow.Flow

interface NewsRemoteDataSource {
    suspend fun getFeeds(page: Int) : List<NewsPreview>
    suspend fun getNewsDetails(id: Int): News
}