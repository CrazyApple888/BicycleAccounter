package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Issue(
    @PrimaryKey(autoGenerate = true) val issueId: Int,
    val cost: Int,
    val Description: String?
)
