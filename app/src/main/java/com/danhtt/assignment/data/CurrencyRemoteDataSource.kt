package com.danhtt.assignment.data

import com.danhtt.assignment.datasource.model.CurrencyResponse

interface CurrencyRemoteDataSource {

    suspend fun getAllCurrencies(counterCurrency: String): CurrencyResponse?
}
