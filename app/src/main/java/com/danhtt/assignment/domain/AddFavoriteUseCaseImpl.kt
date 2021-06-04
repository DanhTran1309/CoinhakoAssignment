package com.danhtt.assignment.domain

import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.usecase.AddFavoriteUseCase

class AddFavoriteUseCaseImpl(
    private val repository: CurrencyRepository
) : AddFavoriteUseCase {

    override suspend fun addFavorite(currency: Currency) =
        repository.addFavorite(
            CurrencyEntity(0, currency.base, currency.name)
        )
}
