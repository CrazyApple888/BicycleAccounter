package ru.nsu.fit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Issue(
    @PrimaryKey(autoGenerate = true) val issueId: Long,
    val cost: Int,
    val Description: String?
)
