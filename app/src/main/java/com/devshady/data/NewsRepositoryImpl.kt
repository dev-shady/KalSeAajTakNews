package com.devshady.data

import com.devshady.domain.News
import com.devshady.domain.NewsLocalDataSource
import com.devshady.domain.NewsPreview
import com.devshady.domain.NewsRemoteDataSource
import com.devshady.domain.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource
) : NewsRepository {
    override fun fetchFeeds(): Flow<List<NewsPreview>> {
        return localDataSource.observeNews()
    }

    override suspend fun refreshNews() {
        //TODO run whole as a transaction
        localDataSource.clearAll()
        val firstPageNews = remoteDataSource.getFeeds(1)
        localDataSource.saveNews(firstPageNews)
    }

    override suspend fun loadNextPage(page: Int): Int {
        val newsList = remoteDataSource.getFeeds(page)
        localDataSource.saveNews(newsList)
        return newsList.size
    }

    override fun fetchNews(id: Int): News {
        return News("123", "url", "title", "description")
    }
}