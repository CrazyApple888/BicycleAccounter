package ru.nsu.fit.data.model

import androidx.room.Embedded
import java.util.*

data class SaleWithItemsDto(
    @Embedded(prefix = "bike_")
    val bicycle: BicycleSimplifiedDto,
    val date: Calendar,
    val warrantyEndDate: Calendar,
    @Embedded(prefix = "customer_")
    val customer: CustomerDto,
    val finalCost: Long
): BaseDto()