package com.danhtt.assignment.di

import androidx.room.Room
import com.danhtt.assignment.common.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "currency")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().currencyBookmarkDao() }
}