package ru.nsu.fit.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class BicycleSimplified(
    val name: String,
    val sellingPrice: Int?,
    val picture: ByteArray,

    val wheelSize: Double
)