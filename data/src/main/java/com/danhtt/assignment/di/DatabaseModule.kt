package com.danhtt.assignment.di

import android.content.Context
import androidx.room.Room
import com.danhtt.assignment.common.database.AppDatabase
import com.danhtt.assignment.datasource.local.CurrencyFavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "currency")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCurrencyBookmarkDAO(appDatabase: AppDatabase): CurrencyFavoriteDao {
        return appDatabase.currencyBookmarkDao()
    }
}
