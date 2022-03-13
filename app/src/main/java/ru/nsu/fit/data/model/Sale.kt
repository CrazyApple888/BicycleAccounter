package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sale(
    @PrimaryKey(autoGenerate = true) val bicycleId: Int,
    val saleDate: String,
    val customer: Long,
    val finalCost: Long
)
