package com.danhtt.assignment.domain.model

data class Currency(
    val base: String = "",
    val counter: String = "",
    val buyPrice: String = "",
    val sellPrice: String = "",
    val icon: String = "",
    val name: String = "",
    var isFavorite: Boolean = false
)
