package com.devshady.domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun fetchFeeds(): Flow<List<NewsPreview>>
    fun fetchNews(id: Int): News
}