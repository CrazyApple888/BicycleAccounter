package ru.nsu.fit.domain.model

sealed interface Result<T> {
    data class Success<T>(val result: T? = null) : Result<T>
    data class Failure<T>(val message: String?, val result: T? = null) : Result<T>
}