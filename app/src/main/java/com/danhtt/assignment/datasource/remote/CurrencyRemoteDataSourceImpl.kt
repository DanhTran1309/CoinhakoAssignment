package com.danhtt.assignment.datasource.remote

import com.danhtt.assignment.data.CurrencyRemoteDataSource

class CurrencyRemoteDataSourceImpl(
    private val endPoint: CurrencyEndPoint
) : CurrencyRemoteDataSource {

    override suspend fun getAllCurrencies(counterCurrency: String) =
        endPoint.getAllPrices(counterCurrency)
}
