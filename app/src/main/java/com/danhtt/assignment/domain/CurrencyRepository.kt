package com.danhtt.assignment.domain

import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.domain.model.Currency

interface CurrencyRepository {

    suspend fun getAllCurrencies(counterCurrency: String): List<Currency>

    suspend fun getAllFavorites(): List<CurrencyEntity>

    suspend fun addFavorite(currencyEntity: CurrencyEntity)

    suspend fun deleteFavoriteByName(name: String)
}
