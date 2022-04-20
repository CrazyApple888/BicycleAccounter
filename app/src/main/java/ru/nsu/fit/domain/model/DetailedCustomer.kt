package ru.nsu.fit.domain.model

data class DetailedCustomer(
    val id: Int,
    val phone: String,
    val name: String,
    val bicycles: List<SimpleBicycle> = emptyList()
) : BaseModel()
