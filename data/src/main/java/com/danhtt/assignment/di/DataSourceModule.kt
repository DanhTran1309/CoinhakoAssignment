package com.danhtt.assignment.di

import com.danhtt.assignment.data.CurrencyLocalDataSource
import com.danhtt.assignment.data.CurrencyRemoteDataSource
import com.danhtt.assignment.datasource.local.CurrencyFavoriteDao
import com.danhtt.assignment.datasource.local.CurrencyLocalDataSourceImpl
import com.danhtt.assignment.datasource.remote.CurrencyEndPoint
import com.danhtt.assignment.datasource.remote.CurrencyRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Provides
    fun provideRemoteDataSource(endPoint: CurrencyEndPoint): CurrencyRemoteDataSource {
        return CurrencyRemoteDataSourceImpl(endPoint)
    }

    @Provides
    fun provideLocalDataSource(dao: CurrencyFavoriteDao): CurrencyLocalDataSource {
        return CurrencyLocalDataSourceImpl(dao)
    }
}
