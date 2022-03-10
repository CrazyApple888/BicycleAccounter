package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BicycleWithIssues(
    @Embedded val bicycle: Bicycle,
    @Relation(
        parentColumn = "bikeId",
        entity = Issue::class,
        entityColumn = "issueId",
        associateBy = Junction(
            value = BicycleIssueXref::class,
            parentColumn = "bikeIdRef",
            entityColumn = "issueIdRef"
        )
    )
    val issues: List<Issue>
)