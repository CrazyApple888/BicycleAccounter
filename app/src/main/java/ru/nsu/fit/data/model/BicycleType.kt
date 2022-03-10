package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bicycle_types")
data class BicycleType(
    @PrimaryKey(autoGenerate = true) val typeId: Long,
    val typeName: String = "No type"
)
