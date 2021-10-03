package com.danhtt.assignment.di

import com.danhtt.assignment.BuildConfig
import com.danhtt.assignment.common.remote.AssignmentRetrofit
import com.danhtt.assignment.common.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule() {
    @Provides
    fun provideAssignmentRetrofit(networkUtils: NetworkUtils): AssignmentRetrofit {
        return AssignmentRetrofit(networkUtils, BuildConfig.BASE_URL)
    }
}
