package com.devshady.domain

import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    fun observeNews(): Flow<List<NewsPreview>>
    suspend fun saveNews(news: List<NewsPreview>)
    suspend fun clearAll()
}