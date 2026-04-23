package com.devshady.data.room

import com.devshady.domain.NewsLocalDataSource
import com.devshady.domain.NewsPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource(private val newsDao: NewsDao): NewsLocalDataSource {
    override fun observeNews(): Flow<List<NewsPreview>> {
        return newsDao.getFeed().map { newsFeedEntities ->
            newsFeedEntities.map { it.toNewsPreview()
            }
        }
    }

    override suspend fun saveNews(news: List<NewsPreview>) {
        newsDao.insertAll(news.map { NewsFeedEntity(
            id = it.id,
            title = it.title,
            description = it.description,
            imageUrl = it.thumbnailUrl
        ) })
    }

    override suspend fun clearAll() {
        newsDao.deleteAll()
    }
}