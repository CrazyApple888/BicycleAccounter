package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SaleDetailedDto(
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
    val customer: CustomerDto,
    @Relation(
        parentColumn = "bicycleId",
        entity = IssueDto::class,
        entityColumn = "issueId",
        associateBy = Junction(
            value = BicycleIssueXref::class,
            parentColumn = "bikeIdRef",
            entityColumn = "issueIdRef"
        )
    )
    val issueDtos: List<IssueDto>
): BaseDto()