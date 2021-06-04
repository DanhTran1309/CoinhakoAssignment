package com.danhtt.assignment.domain.model

import com.danhtt.assignment.datasource.model.CurrencyData

data class Currency(
    val base: String = "",
    val counter: String = "",
    val buyPrice: String = "",
    val sellPrice: String = "",
    val icon: String = "",
    val name: String = "",
    var isFavorite: Boolean = false
)

fun CurrencyData.mapToDomain() =
    Currency(
        this.base ?: "",
        this.counter ?: "",
        this.buyPrice ?: "",
        this.sellPrice ?: "",
        this.icon ?: "",
        this.name ?: ""
    )
