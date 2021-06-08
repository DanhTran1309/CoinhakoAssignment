package com.danhtt.assignment.domain

import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent

interface CurrencyRepository {

    suspend fun getAllCurrencies(counterCurrency: String): StateEvent<List<Currency>>

    suspend fun getAllFavorites(): List<CurrencyEntity>

    suspend fun addFavorite(currencyEntity: CurrencyEntity)

    suspend fun deleteFavoriteByName(name: String)
}
