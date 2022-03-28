package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issues")
data class IssueDto(
    @PrimaryKey(autoGenerate = true) val issueId: Int = 0,
    val cost: Int,
    val description: String?
) : BaseDto()
