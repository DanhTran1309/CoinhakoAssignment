package com.danhtt.assignment.datasource.model

sealed class RemoteDataResponse<T> {
    class Success<T>(val data: T?) : RemoteDataResponse<T>()
    class Error<T>(val errorMessage: String?): RemoteDataResponse<T>()
}
