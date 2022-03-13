package ru.nsu.fit.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["bikeIdRef", "issueIdRef"])
data class BicycleIssueXref(
    val bikeIdRef: Int,
    val issueIdRef: Int
)
