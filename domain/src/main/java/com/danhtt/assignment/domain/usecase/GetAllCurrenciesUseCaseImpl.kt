package com.danhtt.assignment.domain.usecase

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.domain.repository.CurrencyRepository

class GetAllCurrenciesUseCaseImpl(
    private val repository: CurrencyRepository
) : GetAllCurrenciesUseCase {

    override suspend fun fetch(counterCurrency: String): StateEvent<List<Currency>> {
        return when (val result = repository.getAllCurrencies(counterCurrency)) {
            is StateEvent.Success -> StateEvent.Success(result.data)
            else -> result
        }
    }
}

interface GetAllCurrenciesUseCase {
    suspend fun fetch(counterCurrency: String): StateEvent<List<Currency>>
}
