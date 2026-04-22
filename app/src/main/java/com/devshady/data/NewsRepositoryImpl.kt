package com.devshady.data

import com.devshady.domain.News
import com.devshady.domain.NewsPreview
import com.devshady.domain.NewsRepository
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl: NewsRepository {
    override fun fetchFeeds()= flow {
         emit(feedsMock)
    }

    override fun fetchNews(id: Int): News {
        return News("123", "url", "title", "description")
    }
}