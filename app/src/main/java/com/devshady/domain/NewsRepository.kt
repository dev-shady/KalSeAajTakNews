package com.devshady.domain

interface NewsRepository {
    fun fetchFeeds(): List<NewsPreview>
    fun fetchNews(id: Int): News
}