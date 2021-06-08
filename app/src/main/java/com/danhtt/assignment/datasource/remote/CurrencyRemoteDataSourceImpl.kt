package com.danhtt.assignment.datasource.remote

import com.danhtt.assignment.data.CurrencyRemoteDataSource
import com.danhtt.assignment.datasource.model.CurrencyList
import com.danhtt.assignment.datasource.model.RemoteDataResponse

class CurrencyRemoteDataSourceImpl(
    private val endPoint: CurrencyEndPoint
) : CurrencyRemoteDataSource {

    override suspend fun getAllCurrencies(counterCurrency: String): RemoteDataResponse<CurrencyList> {
        return try {
            val response = endPoint.getAllPrices(counterCurrency)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                RemoteDataResponse.Success(result)
            } else {
                RemoteDataResponse.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            RemoteDataResponse.Error(e.message)
        }
    }
}
