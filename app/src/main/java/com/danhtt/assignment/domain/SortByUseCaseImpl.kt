package com.danhtt.assignment.domain

import androidx.annotation.VisibleForTesting
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.usecase.SortByUseCase

class SortByUseCaseImpl : SortByUseCase {

    @VisibleForTesting
    override fun sortByNameAscending(originalList: List<Currency>) =
        originalList.sortedBy { it.name }

    @VisibleForTesting
    override fun sortByNameDescending(originalList: List<Currency>) =
        originalList.sortedByDescending { it.name }

    @VisibleForTesting
    override fun sortByPriceAscending(originalList: List<Currency>) =
        originalList.sortedBy { it.sellPrice.toDoubleOrNull() }

    @VisibleForTesting
    override fun sortByPriceDescending(originalList: List<Currency>) =
        originalList.sortedByDescending { it.sellPrice.toDoubleOrNull() }
}
