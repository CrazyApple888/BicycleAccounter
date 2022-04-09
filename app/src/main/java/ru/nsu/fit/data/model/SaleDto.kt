package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sales")
data class SaleDto(
    @PrimaryKey val bicycleId: Int = 0,
    val saleDate: Calendar,
    val customerId: Int,
    val finalCost: Long
) : BaseDto()
