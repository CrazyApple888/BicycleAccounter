package ru.nsu.fit.data.model

import androidx.room.Embedded

data class CustomerAllBicyclesDto(
    @Embedded
    val customer: CustomerDto
): BaseDto()
