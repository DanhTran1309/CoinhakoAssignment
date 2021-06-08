package com.danhtt.assignment.domain

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.presentation.usecase.GetAllCurrenciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllCurrenciesUseCaseImpl(
    private val repository: CurrencyRepository
) : GetAllCurrenciesUseCase {

    override suspend fun fetch(counterCurrency: String): StateEvent<List<Currency>> {
        return withContext(Dispatchers.IO) {
            val favorites = repository.getAllFavorites()
            when (val result = repository.getAllCurrencies(counterCurrency)) {
                is StateEvent.Success -> StateEvent.Success(result.data.map {
                    it.isFavorite = favorites.any { favorite -> it.name == favorite.name }
                    it
                })
                else -> result
            }
        }
    }
}
