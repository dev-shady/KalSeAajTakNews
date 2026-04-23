package com.devshady.data

import com.devshady.domain.News
import com.devshady.domain.NewsPreview
import com.devshady.domain.NewsRemoteDataSource
import com.devshady.domain.NewsRepository
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val remoteDataSource: NewsRemoteDataSource): NewsRepository {
    override fun fetchFeeds()= flow {
         emit(remoteDataSource.getFeeds(0))
    }

    override fun fetchNews(id: Int): News {
        return News("123", "url", "title", "description")
    }
}