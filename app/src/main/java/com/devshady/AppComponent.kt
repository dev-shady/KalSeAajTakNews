package com.devshady

import android.content.Context
import androidx.room.Room
import com.devshady.data.NewsRepositoryImpl
import com.devshady.data.retrofit.NewsApi
import com.devshady.data.retrofit.RetrofitRemoteDataSource
import com.devshady.data.retrofit.okhttp.AuthenticationInterceptor
import com.devshady.data.room.NewsDatabase
import com.devshady.data.room.RoomLocalDataSource
import com.devshady.domain.GetFeedsUseCase
import com.devshady.domain.LoadNextPageUseCase
import com.devshady.domain.RefreshFeedsUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppComponent(context: Context) {

    //TODO replace this whole manual DI with Hilt
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
    private val database: NewsDatabase by lazy {
        Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news_db"
    ).build()
    }
    val localDataSource by lazy {
        RoomLocalDataSource(database.newsDao())
    }
    val newsRepository = NewsRepositoryImpl(remoteDataSource, localDataSource)
    val getFeedsUseCase = GetFeedsUseCase(newsRepository)
    val refreshFeedsUseCase = RefreshFeedsUseCase(newsRepository)
    val loadNextPageUseCase = LoadNextPageUseCase(newsRepository
    )
}