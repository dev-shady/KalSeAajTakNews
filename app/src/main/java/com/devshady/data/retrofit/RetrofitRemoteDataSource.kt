package com.devshady.data.retrofit

import android.util.Log
import com.devshady.domain.News
import com.devshady.domain.NewsPreview
import com.devshady.domain.NewsRemoteDataSource
import java.util.UUID

class RetrofitRemoteDataSource(private val api: NewsApi) : NewsRemoteDataSource {
    override suspend fun getFeeds(page: Int): List<NewsPreview> {
        return try {
            val response = api.getTopHeadlines(page = page)
            response.articles.map { news-> NewsPreview(
                id = news.id ?: UUID.randomUUID().toString(),
                title = news.title ?: "",
                description = news.description ?: "",
                thumbnailUrl = news.urlToImage ?: ""
            )
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("getFeeds failed ", it) }
            Log.e("retrofit","getFeeds failed ", e)
            return emptyList()
        }

    }

    override suspend fun getNewsDetails(id: Int): News {
        TODO("Not yet implemented")
    }
}