package com.danhtt.assignment.presentation.usecase

import com.danhtt.assignment.domain.model.Currency

interface FilterUseCase {

    fun searchByName(textSearch: String?, originalList: List<Currency>): List<Currency>
}
