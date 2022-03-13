package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey val bicycleId: Int,
    val saleDate: String,
    val customerId: Int,
    val finalCost: Long
)
