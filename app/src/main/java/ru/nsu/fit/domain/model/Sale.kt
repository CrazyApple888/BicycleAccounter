package ru.nsu.fit.domain.model

import java.util.*

data class Sale(
    val bicycle: SimpleBicycle,
    val customer: Customer,
    val saleDate: Calendar,
    val finalCost: Long
): BaseModel()