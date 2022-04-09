package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BicycleAllSpecsDto(
    @Embedded
    val bicycleDto: BicycleDto,

    @Embedded
    val typeDto: BicycleTypeDto,
    @Embedded
    val color: ColorDto,
    @Embedded
    val stateDto: BicycleStateDto,
    @Embedded
    val wheelSizeDto: WheelSizeDto,

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
) : BaseDto()
