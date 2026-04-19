package com.devshady.domain

class GetFeedsUseCase(val repository: NewsRepository) {

    operator fun invoke(): List<NewsPreview>{
        return repository.fetchFeeds()
    }

}