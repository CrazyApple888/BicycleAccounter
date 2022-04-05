package ru.nsu.fit.domain.model

sealed interface Result<T> {
    data class Success<T>(val result: T? = null) : Result<T>
    data class Failure<T>(val message: String? = null, val result: T? = null) : Result<T>

    fun successOrNull(onFailure: ((String?, T?) -> Unit)? = null): T? {
        return when (this) {
            is Success -> {
                this.result
            }
            is Failure -> {
                onFailure?.invoke(this.message, this.result)
                null
            }
        }
    }
}