package com.danhtt.assignment.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danhtt.assignment.datasource.local.CurrencyEntity
import com.danhtt.assignment.datasource.local.CurrencyFavoriteDao

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyBookmarkDao(): CurrencyFavoriteDao
}
