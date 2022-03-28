package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bicycle_types")
data class BicycleTypeDto(
    @PrimaryKey(autoGenerate = true) val typeId: Int = 0,
    val typeName: String = "No type"
) : BaseDto()
