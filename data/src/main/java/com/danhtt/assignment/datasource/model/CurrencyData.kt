package com.danhtt.assignment.datasource.model

import com.danhtt.assignment.domain.model.Currency
import com.google.gson.annotations.SerializedName

data class CurrencyData(
    @SerializedName("base")
    val base: String?,
    @SerializedName("counter")
    val counter: String?,
    @SerializedName("buy_price")
    val buyPrice: String?,
    @SerializedName("sell_price")
    val sellPrice: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("name")
    val name: String?
) {

    fun mapToDomain() =
        Currency(
            this.base ?: "",
            this.counter ?: "",
            this.buyPrice ?: "",
            this.sellPrice ?: "",
            this.icon ?: "",
            this.name ?: ""
        )
}
