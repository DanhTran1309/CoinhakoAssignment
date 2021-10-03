package com.danhtt.assignment.domain.di

import com.danhtt.assignment.domain.repository.CurrencyRepository
import com.danhtt.assignment.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideGetAllCurrenciesUseCase(repository: CurrencyRepository): GetAllCurrenciesUseCase {
        return GetAllCurrenciesUseCaseImpl(repository)
    }

    @Provides
    fun provideAddFavoriteUseCase(repository: CurrencyRepository): AddFavoriteUseCase {
        return AddFavoriteUseCaseImpl(repository)
    }

    @Provides
    fun provideDeleteFavoriteUseCase(repository: CurrencyRepository): DeleteFavoriteUseCase {
        return DeleteFavoriteUseCaseImpl(repository)
    }

    @Provides
    fun provideFilterUseCase(): FilterUseCase {
        return FilterUseCaseImpl()
    }

    @Provides
    fun provideSortByUseCase(): SortByUseCase {
        return SortByUseCaseImpl()
    }
}
