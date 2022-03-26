package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wheel_sizes")
data class WheelSize(
    @PrimaryKey(autoGenerate = true) val sizeId: Int,
    val sizeInches: Double
)
