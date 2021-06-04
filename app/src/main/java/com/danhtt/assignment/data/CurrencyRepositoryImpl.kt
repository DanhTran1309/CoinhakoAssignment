package com.danhtt.assignment.data

import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.domain.CurrencyRepository
import com.danhtt.assignment.domain.model.mapToDomain

class CurrencyRepositoryImpl(
    private val remoteDataSource: CurrencyRemoteDataSource,
    private val localDataSource: CurrencyLocalDataSource
) : CurrencyRepository {

    override suspend fun getAllCurrencies(counterCurrency: String) =
        remoteDataSource.getAllCurrencies(counterCurrency)?.currencies?.map { it.mapToDomain() } ?: emptyList()

    override suspend fun getAllFavorites() = localDataSource.getAllFavorites()

    override suspend fun addFavorite(currencyEntity: CurrencyEntity) = localDataSource.addFavorite(currencyEntity)

    override suspend fun deleteFavoriteByName(name: String) = localDataSource.deleteFavoriteByName(name)
}
