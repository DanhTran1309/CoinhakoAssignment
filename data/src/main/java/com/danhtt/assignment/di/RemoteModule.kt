package com.danhtt.assignment.di

import com.danhtt.assignment.common.remote.AssignmentRetrofit
import com.danhtt.assignment.datasource.remote.CurrencyEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {

    @Provides
    fun provideCurrencyEndPoint(retrofit: AssignmentRetrofit): CurrencyEndPoint {
        return retrofit.getClient().create(CurrencyEndPoint::class.java)
    }
}
