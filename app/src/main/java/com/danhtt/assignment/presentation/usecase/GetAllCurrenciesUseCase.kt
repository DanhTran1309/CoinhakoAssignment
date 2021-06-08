package com.danhtt.assignment.presentation.usecase

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent

interface GetAllCurrenciesUseCase {

    suspend fun fetch(counterCurrency: String): StateEvent<List<Currency>>
}
