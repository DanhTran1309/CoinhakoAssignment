package com.danhtt.assignment.datasource.local

import com.danhtt.assignment.data.CurrencyLocalDataSource

class CurrencyLocalDataSourceImpl(
    private val dao: CurrencyFavoriteDao
) : CurrencyLocalDataSource {
    override suspend fun getAllFavorites() = dao.getAll()

    override suspend fun addFavorite(currencyEntity: CurrencyEntity)  = dao.insert(currencyEntity)

    override suspend fun deleteFavoriteByName(name: String) = dao.deleteByName(name)
}
