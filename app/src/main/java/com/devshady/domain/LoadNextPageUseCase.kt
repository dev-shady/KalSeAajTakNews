package com.devshady.domain

class LoadNextPageUseCase(val repository: NewsRepository) {

    suspend operator fun invoke(nextPage: Int): Int = repository.loadNextPage(nextPage)

}