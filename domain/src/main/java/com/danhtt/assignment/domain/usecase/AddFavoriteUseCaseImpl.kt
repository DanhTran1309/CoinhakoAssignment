package com.danhtt.assignment.domain.usecase

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.repository.CurrencyRepository

class AddFavoriteUseCaseImpl(
    private val repository: CurrencyRepository
) : AddFavoriteUseCase {

    override suspend fun addFavorite(currency: Currency) = repository.addFavorite(currency)
}

interface AddFavoriteUseCase {
    suspend fun addFavorite(currency: Currency)
}
