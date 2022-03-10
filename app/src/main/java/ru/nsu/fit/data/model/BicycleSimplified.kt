package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class BicycleSimplified(
    @Embedded val bicycle: Bicycle,
    @Relation(
        parentColumn = "wheelSizeId",
        entityColumn = "sizeId",
    )
    val wheelSize: WheelSize
)