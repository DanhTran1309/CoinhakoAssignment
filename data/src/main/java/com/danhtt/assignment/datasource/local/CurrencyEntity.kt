package com.danhtt.assignment.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_currency")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "base") val base: String,
    @ColumnInfo(name = "name") val name: String
)
