package com.danhtt.assignment.data

import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.datasource.model.RemoteDataResponse
import com.danhtt.assignment.domain.CurrencyRepository
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.domain.model.mapToDomain

class CurrencyRepositoryImpl(
    private val remoteDataSource: CurrencyRemoteDataSource,
    private val localDataSource: CurrencyLocalDataSource
) : CurrencyRepository {

    override suspend fun getAllCurrencies(counterCurrency: String): StateEvent<List<Currency>> {
        return when (val response = remoteDataSource.getAllCurrencies(counterCurrency)) {
            is RemoteDataResponse.Success -> StateEvent.Success(response.data?.currencies?.map { it.mapToDomain() }
                ?: emptyList())
            is RemoteDataResponse.Error -> StateEvent.Failure(response.errorMessage)
        }
    }

    override suspend fun getAllFavorites() = localDataSource.getAllFavorites()

    override suspend fun addFavorite(currencyEntity: CurrencyEntity) =
        localDataSource.addFavorite(currencyEntity)

    override suspend fun deleteFavoriteByName(name: String) =
        localDataSource.deleteFavoriteByName(name)
}
