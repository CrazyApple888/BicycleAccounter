package ru.nsu.fit.domain.model

import java.util.*

data class Sale(
    val bicycle: SimpleBicycle,
    val date: Calendar,
    val warrantyEndDate: Calendar,
    val customer: Customer,
    val finalCost: Long
): BaseModel()