package ru.nsu.fit.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BicycleAllSpecs(
    @Embedded
    val bicycleDto: BicycleDto,

    @Embedded()
    val type: BicycleType,
    @Embedded()
    val color: ColorDto,
    @Embedded()
    val state: BicycleState,
    @ColumnInfo(name = "sizeInches")
    val wheelSize: Double,

    @Relation(
        parentColumn = "bikeId",
        entity = IssueDto::class,
        entityColumn = "issueId",
        associateBy = Junction(
            value = BicycleIssueXref::class,
            parentColumn = "bikeIdRef",
            entityColumn = "issueIdRef"
        )
    )
    val issueDtos: List<IssueDto>
)
