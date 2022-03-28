package ru.nsu.fit.domain.model

sealed interface Result<T> {
    data class Success<T>(val result: T? = null) : Result<T>
    data class Failure<T>(val message: String?, val result: T? = null) : Result<T>
}

fun <T> Result<T>.successOrNull(onFailure: ((String?, T?) -> Unit)? = null): T? {
    return when (this) {
        is Result.Success -> {
            this.result
        }
        is Result.Failure -> {
            onFailure?.invoke(this.message, this.result)
            null
        }
    }
}
