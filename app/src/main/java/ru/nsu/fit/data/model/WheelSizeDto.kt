package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wheel_sizes")
data class WheelSizeDto(
    @PrimaryKey(autoGenerate = true) val sizeId: Int = 0,
    val sizeInches: Double
) : BaseDto()