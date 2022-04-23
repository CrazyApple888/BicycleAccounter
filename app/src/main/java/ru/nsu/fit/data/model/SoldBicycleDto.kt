package ru.nsu.fit.data.model

import android.graphics.Bitmap
import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    viewName = "sold_bicycles",
    value = "SELECT * FROM sales " +
            "INNER JOIN bicycles ON bicycles.bikeId = sales.bicycleId " +
            "INNER JOIN wheel_sizes ON wheel_sizes.sizeId = bicycles.wheelSizeIdRef "
)
data class SoldBicycleDto(
    val customerId: Int,
    val bikeId: Int,
    val name: String,
    val finalCost: Long,
    val purchasePrice: Int = 0,
    val picture: Bitmap?,

    @Embedded
    val wheelSize: WheelSizeDto
) : BaseDto()