package com.danhtt.assignment.data

import com.danhtt.assignment.datasource.local.CurrencyEntity

interface CurrencyLocalDataSource {

    suspend fun getAllFavorites(): List<CurrencyEntity>

    suspend fun addFavorite(currencyEntity: CurrencyEntity)

    suspend fun deleteFavoriteByName(name: String)
}
