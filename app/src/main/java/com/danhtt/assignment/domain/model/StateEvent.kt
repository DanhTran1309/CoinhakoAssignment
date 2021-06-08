package com.danhtt.assignment.domain.model

sealed class StateEvent<T> {
    class Success<T>(val data: T): StateEvent<T>()
    class Failure<T>(val errorMessage: String?): StateEvent<T>()
    class Loading<T>(): StateEvent<T>()
}
