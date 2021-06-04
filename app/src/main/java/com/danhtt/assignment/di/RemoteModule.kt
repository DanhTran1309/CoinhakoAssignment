package com.danhtt.assignment.di

import com.danhtt.assignment.BuildConfig
import com.danhtt.assignment.common.remote.AssignmentRetrofit
import com.danhtt.assignment.common.utils.NetworkUtils
import com.danhtt.assignment.datasource.remote.CurrencyEndPoint
import org.koin.dsl.module

val remoteModule = module {
    single { NetworkUtils(get()) }
    single { AssignmentRetrofit(get(), BuildConfig.BASE_URL) }
    factory { get<AssignmentRetrofit>().getClient().create(CurrencyEndPoint::class.java) }
}
