package com.danhtt.assignment.domain

import com.danhtt.assignment.presentation.usecase.DeleteFavoriteUseCase

class DeleteFavoriteUseCaseImpl(
    private val repository: CurrencyRepository
) : DeleteFavoriteUseCase {

    override suspend fun deleteByName(name: String) = repository.deleteFavoriteByName(name)
}
