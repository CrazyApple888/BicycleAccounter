package ru.nsu.fit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["bikeIdRef", "issueIdRef"])
data class BicycleIssueXref(
    val bikeIdRef: Long,
    val issueIdRef: Long
)
