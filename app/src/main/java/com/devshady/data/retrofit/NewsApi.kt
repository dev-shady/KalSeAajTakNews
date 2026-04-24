package com.devshady.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun getTopHeadlines(
        @Query("sources") country: String = "the-verge",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 15,

    ): NewsResponseDTO

    @GET("v2/article/{id}")
    suspend fun getNewsArticle(
        @Path("id") id: String
    ): NewsDTO
}