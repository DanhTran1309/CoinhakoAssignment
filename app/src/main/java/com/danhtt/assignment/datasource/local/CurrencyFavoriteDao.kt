package com.danhtt.assignment.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyFavoriteDao {
    @Query("SELECT * FROM favorite_currency")
    suspend fun getAll(): List<CurrencyEntity>

    @Insert
    suspend fun insert(currencyEntity: CurrencyEntity)

    @Query("DELETE FROM favorite_currency WHERE name = :name")
    suspend fun deleteByName(name: String)
}
