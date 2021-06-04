package com.danhtt.assignment.presentation.usecase

import com.danhtt.assignment.domain.model.Currency

interface GetAllCurrenciesUseCase {

    suspend fun fetch(counterCurrency: String): List<Currency>
}
