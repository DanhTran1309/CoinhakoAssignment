package com.danhtt.assignment.datasource.model

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
)
