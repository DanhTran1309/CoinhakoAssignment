package com.danhtt.assignment.presentation.usecase

import com.danhtt.assignment.domain.model.Currency

interface AddFavoriteUseCase {
    suspend fun addFavorite(currency: Currency)
}
