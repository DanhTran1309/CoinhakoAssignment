package com.danhtt.assignment.domain

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.usecase.GetAllCurrenciesUseCase

class GetAllCurrenciesUseCaseImpl(
    private val repository: CurrencyRepository
) : GetAllCurrenciesUseCase {

    override suspend fun fetch(counterCurrency: String): List<Currency> {
        val favorites = repository.getAllFavorites()
        return repository.getAllCurrencies(counterCurrency).map {
            it.isFavorite = favorites.any { favorite -> it.name == favorite.name }
            it
        }
    }
}
