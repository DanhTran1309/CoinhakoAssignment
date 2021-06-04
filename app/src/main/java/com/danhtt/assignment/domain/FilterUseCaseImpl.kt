package com.danhtt.assignment.domain

import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.usecase.FilterUseCase
import java.util.*

class FilterUseCaseImpl : FilterUseCase {

    override fun searchByName(textSearch: String?, originalList: List<Currency>): List<Currency> {
        val search = textSearch?.lowercase(Locale.getDefault())?.trim()
        if (search.isNullOrEmpty()) {
            return originalList
        }
        return originalList.filter {
            it.name.lowercase(Locale.getDefault()).contains(search)
        }
    }
}
