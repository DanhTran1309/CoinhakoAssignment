package com.danhtt.assignment.datasource.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("data")
    val currencies: List<CurrencyData>?
)
