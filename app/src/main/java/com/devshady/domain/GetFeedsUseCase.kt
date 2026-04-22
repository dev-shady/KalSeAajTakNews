package com.devshady.domain

class GetFeedsUseCase(val repository: NewsRepository) {

    operator fun invoke() =  repository.fetchFeeds()

}