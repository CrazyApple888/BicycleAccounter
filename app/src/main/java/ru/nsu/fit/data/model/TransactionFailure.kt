package ru.nsu.fit.data.model

enum class TransactionFailure(private val id: Int = 0) {
    TRANSACTION_REJECTED(-1),
    ALREADY_EXISTS(0);
}