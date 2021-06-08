package com.danhtt.assignment.domain

import com.danhtt.assignment.presentation.usecase.DeleteFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteFavoriteUseCaseImpl(
    private val repository: CurrencyRepository
) : DeleteFavoriteUseCase {

    override suspend fun deleteByName(name: String) =
        withContext(Dispatchers.IO) {
            repository.deleteFavoriteByName(name)
        }
}
