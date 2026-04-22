package com.devshady

import com.devshady.data.NewsRepositoryImpl
import com.devshady.domain.GetFeedsUseCase

object AppComponent {

    val newsRepository = NewsRepositoryImpl()
    val getFeedsUseCase = GetFeedsUseCase(newsRepository)
}