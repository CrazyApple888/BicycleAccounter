package ru.nsu.fit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sales(
    @PrimaryKey(autoGenerate = true) val bicycleId: Long,
    val saleDate: String,
    val customer: Long,
    val finalCost: Long
)
