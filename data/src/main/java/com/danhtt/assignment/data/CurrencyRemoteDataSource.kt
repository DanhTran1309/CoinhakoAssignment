package com.danhtt.assignment.data

import com.danhtt.assignment.datasource.model.CurrencyList
import com.danhtt.assignment.datasource.model.RemoteDataResponse

interface CurrencyRemoteDataSource {

    suspend fun getAllCurrencies(counterCurrency: String): RemoteDataResponse<CurrencyList>
}
