package com.danhtt.assignment.di

import com.danhtt.assignment.data.CurrencyLocalDataSource
import com.danhtt.assignment.data.CurrencyRemoteDataSource
import com.danhtt.assignment.data.CurrencyRepositoryImpl
import com.danhtt.assignment.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideCurrencyRepository(
        remoteDataSource: CurrencyRemoteDataSource,
        localDataSource: CurrencyLocalDataSource
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(remoteDataSource, localDataSource)
    }
}
