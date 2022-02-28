package ru.nsu.fit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Bicycle(
    @PrimaryKey(autoGenerate = true) val bikeId: Long,
    val purchasePrice: Int,
    val sellingPrice: Int?,
    val description: String?,

    //references
    val type: Long,
    val state: Long,
    val wheelSizeId: Long,
    val colorId: Long,
)