package com.danhtt.assignment.di

import com.danhtt.assignment.BuildConfig
import com.danhtt.assignment.common.remote.AssignmentRetrofit
import com.danhtt.assignment.common.utils.NetworkUtils
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

    @Provides
    fun provideAssignmentRetrofit(networkUtils: NetworkUtils): AssignmentRetrofit {
        return AssignmentRetrofit(networkUtils, BuildConfig.BASE_URL)
    }
}
