package ru.nsu.fit.data.model

import android.graphics.Bitmap

data class BicycleSimplifiedDto(
    val bikeId: Int,
    val name: String,
    val sellingPrice: Int?,
    val picture: Bitmap?,

    val wheelSize: Double
) : BaseDto()