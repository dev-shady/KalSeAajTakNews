package com.devshady.navigation

import kotlinx.serialization.Serializable


sealed class Route {
    @Serializable object NewsFeed: Route()
    @Serializable data class Details(val articleId: String): Route()
}