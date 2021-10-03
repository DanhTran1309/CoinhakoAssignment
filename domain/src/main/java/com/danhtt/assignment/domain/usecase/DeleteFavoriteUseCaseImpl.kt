package com.danhtt.assignment.domain.usecase

import com.danhtt.assignment.domain.repository.CurrencyRepository

class DeleteFavoriteUseCaseImpl(
    private val repository: CurrencyRepository
) : DeleteFavoriteUseCase {

    override suspend fun deleteByName(name: String) =
        repository.deleteFavoriteByName(name)
}

interface DeleteFavoriteUseCase {
    suspend fun deleteByName(name: String)
}
