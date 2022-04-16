package ru.nsu.fit.domain.model

data class DetailedCustomer(
    val id: Int,
    val name: String,
    val bicycles: List<Bicycle> = emptyList()
)
