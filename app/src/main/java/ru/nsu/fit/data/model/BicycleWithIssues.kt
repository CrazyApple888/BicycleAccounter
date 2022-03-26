package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BicycleWithIssues(
    @Embedded val bicycleDto: BicycleDto,
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
