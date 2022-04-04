package ru.nsu.fit.domain.model

data class Customer(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val trades: List<Trade>
) : BaseModel()