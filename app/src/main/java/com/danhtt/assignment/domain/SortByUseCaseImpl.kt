package com.danhtt.assignment.domain

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.usecase.SortByUseCase

class SortByUseCaseImpl : SortByUseCase {

    override fun sortByNameAscending(originalList: List<Currency>) =
        originalList.sortedBy { it.name }

    override fun sortByNameDescending(originalList: List<Currency>) =
        originalList.sortedByDescending { it.name }

    override fun sortByPriceAscending(originalList: List<Currency>) =
        originalList.sortedBy { it.sellPrice.toDoubleOrNull() }

    override fun sortByPriceDescending(originalList: List<Currency>) =
        originalList.sortedByDescending { it.sellPrice.toDoubleOrNull() }
}
