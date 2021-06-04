package com.danhtt.assignment.di

import com.danhtt.assignment.data.CurrencyLocalDataSource
import com.danhtt.assignment.data.CurrencyRemoteDataSource
import com.danhtt.assignment.data.CurrencyRepositoryImpl
import com.danhtt.assignment.datasource.local.CurrencyLocalDataSourceImpl
import com.danhtt.assignment.datasource.remote.CurrencyRemoteDataSourceImpl
import com.danhtt.assignment.domain.*
import com.danhtt.assignment.presentation.usecase.*
import com.danhtt.assignment.presentation.viewmodel.CurrencyViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currencyModule = module {
    factory<CurrencyRemoteDataSource> { CurrencyRemoteDataSourceImpl(get()) }
    factory<CurrencyLocalDataSource> { CurrencyLocalDataSourceImpl(get()) }
    factory<CurrencyRepository> { CurrencyRepositoryImpl(get(), get()) }
    factory<GetAllCurrenciesUseCase> { GetAllCurrenciesUseCaseImpl(get()) }
    factory<AddFavoriteUseCase> { AddFavoriteUseCaseImpl(get()) }
    factory<DeleteFavoriteUseCase> { DeleteFavoriteUseCaseImpl(get()) }
    factory<SortByUseCase> { SortByUseCaseImpl() }
    factory<FilterUseCase> { FilterUseCaseImpl() }
    viewModel { CurrencyViewModel(get(), get(), get(), get()) }
}
