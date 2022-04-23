package ru.nsu.fit.domain.model


data class SaleDetailed(
    val customerName: String,
    val bicycleName: String,
    val additionalInfo: String,
    val sellPrice: Long,
    val buyPrice: Long,
    val cost: Long,
    val totalCost: Long
) : BaseModel()