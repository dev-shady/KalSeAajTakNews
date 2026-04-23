package com.devshady.domain

class RefreshFeedsUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke() = repository.refreshNews()
}