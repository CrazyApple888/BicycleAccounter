package ru.nsu.fit.data.model

import androidx.room.Junction
import androidx.room.Relation

data class CustomerDetailedDto(
    val customer: CustomerDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "bikeId",
        associateBy = Junction(
            value = SaleDto::class,
            parentColumn = "bicycleId",
            entityColumn = "customerId"
        )
    )
    val bikes: List<SoldBicycleDto> = emptyList()
) : BaseDto()