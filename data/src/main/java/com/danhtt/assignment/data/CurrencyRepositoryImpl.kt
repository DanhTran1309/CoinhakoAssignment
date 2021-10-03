package com.danhtt.assignment.data

import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.datasource.model.RemoteDataResponse
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyRepositoryImpl(
    private val remoteDataSource: CurrencyRemoteDataSource,
    private val localDataSource: CurrencyLocalDataSource
) : CurrencyRepository {

    override suspend fun getAllCurrencies(counterCurrency: String): StateEvent<List<Currency>> {
        return withContext(Dispatchers.IO) {
            val favorites = localDataSource.getAllFavorites()
            when (val response = remoteDataSource.getAllCurrencies(counterCurrency)) {
                is RemoteDataResponse.Success -> StateEvent.Success(
                    response.data?.currencies
                        ?.map { it.mapToDomain() }
                        ?.map {
                            it.isFavorite = favorites.any { favorite -> it.name == favorite.name }
                            it
                        }
                    ?: emptyList())
                is RemoteDataResponse.Error -> StateEvent.Failure(response.errorMessage)
            }
        }
    }

    override suspend fun addFavorite(currency: Currency) =
        localDataSource.addFavorite(CurrencyEntity(0, currency.base, currency.name))

    override suspend fun deleteFavoriteByName(name: String) =
        localDataSource.deleteFavoriteByName(name)
}
