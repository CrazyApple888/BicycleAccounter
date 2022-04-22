package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SaleWithItemsDto(
    @Embedded
    val saleDto: SaleDto,
    @Relation(
        parentColumn = "bicycleId",
        entityColumn = "bikeId"
    )
    val bicycle: SoldBicycleDto,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "id"
    )
    val customer: CustomerDto
): BaseDto()