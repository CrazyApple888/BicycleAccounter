package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Relation
import java.util.*

data class DetailedCustomerDto(
    @Embedded
    val customer: CustomerDto,
    val sateDate: Date,
    val bicycles: List<BicycleSimplifiedDto>

): BaseDto()
