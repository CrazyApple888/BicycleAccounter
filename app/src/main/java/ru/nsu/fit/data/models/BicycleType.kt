package ru.nsu.fit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BicycleType(
    @PrimaryKey(autoGenerate = true) val typeId: Long,
    val typeName: String = "No type"
)
