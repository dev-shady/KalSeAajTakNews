package com.devshady.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("page") page: Int
    ): NewsResponseDTO

    @GET("v2/article/{id}")
    suspend fun getNewsArticle(
        @Path("id") id: String
    ): NewsDTO
}