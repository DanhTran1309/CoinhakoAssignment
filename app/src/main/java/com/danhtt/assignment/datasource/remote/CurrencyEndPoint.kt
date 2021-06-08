package com.danhtt.assignment.datasource.remote

import com.danhtt.assignment.datasource.model.CurrencyList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyEndPoint {

    @GET("api/v3/price/all_prices_for_mobile")
    suspend fun getAllPrices(
        @Query("counter_currency") counterCurrency: String
    ): Response<CurrencyList>
}
