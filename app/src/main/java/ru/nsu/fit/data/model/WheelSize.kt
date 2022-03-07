package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WheelSize(
    @PrimaryKey(autoGenerate = true) val sizeId: Long,
    val sizeInches: Double
)
