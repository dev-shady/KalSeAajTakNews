package com.devshady

import com.devshady.data.NewsRepositoryImpl
import com.devshady.data.retrofit.NewsApi
import com.devshady.data.retrofit.RetrofitRemoteDataSource
import com.devshady.data.retrofit.okhttp.AuthenticationInterceptor
import com.devshady.domain.GetFeedsUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppComponent {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthenticationInterceptor(BuildConfig.NEWS_API_KEY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val newsApi = retrofit.create(NewsApi::class.java)

    val remoteDataSource by lazy {
        RetrofitRemoteDataSource(newsApi)
    }

    val newsRepository = NewsRepositoryImpl(remoteDataSource)
    val getFeedsUseCase = GetFeedsUseCase(newsRepository)

}