package com.danhtt.assignment.presentation.usecase

import com.danhtt.assignment.domain.model.Currency

interface SortByUseCase {

    fun sortByNameAscending(originalList: List<Currency>): List<Currency>

    fun sortByNameDescending(originalList: List<Currency>): List<Currency>

    fun sortByPriceAscending(originalList: List<Currency>): List<Currency>

    fun sortByPriceDescending(originalList: List<Currency>): List<Currency>
}
