package ru.nsu.fit.data.model

import android.graphics.Bitmap
import androidx.room.Embedded

data class BicycleSimplifiedDto(
    val bikeId: Int,
    val name: String,
    val sellingPrice: Int?,
    val picture: Bitmap?,

    @Embedded
    val wheelSize: WheelSizeDto
) : BaseDto()