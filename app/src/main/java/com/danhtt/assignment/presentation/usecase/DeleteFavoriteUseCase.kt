package com.danhtt.assignment.presentation.usecase

interface DeleteFavoriteUseCase {

    suspend fun deleteByName(name: String)
}
