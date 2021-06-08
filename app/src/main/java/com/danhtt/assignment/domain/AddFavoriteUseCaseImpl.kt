package com.danhtt.assignment.domain

import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.usecase.AddFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddFavoriteUseCaseImpl(
    private val repository: CurrencyRepository
) : AddFavoriteUseCase {

    override suspend fun addFavorite(currency: Currency) =
        withContext(Dispatchers.IO) {
            repository.addFavorite(
                CurrencyEntity(0, currency.base, currency.name)
            )
        }
}
