package com.danhtt.assignment.domain.repository

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent

interface CurrencyRepository {

    suspend fun getAllCurrencies(counterCurrency: String): StateEvent<List<Currency>>

    suspend fun addFavorite(currency: Currency)

    suspend fun deleteFavoriteByName(name: String)
}
