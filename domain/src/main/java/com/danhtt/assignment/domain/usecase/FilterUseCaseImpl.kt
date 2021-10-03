package com.danhtt.assignment.domain.usecase

import androidx.annotation.VisibleForTesting
import com.danhtt.assignment.domain.model.Currency
import java.util.*

class FilterUseCaseImpl : FilterUseCase {

    @VisibleForTesting
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

interface FilterUseCase {
    fun searchByName(textSearch: String?, originalList: List<Currency>): List<Currency>
}
