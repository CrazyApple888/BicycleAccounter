package ru.nsu.fit.domain.model

import android.graphics.Bitmap

data class SimpleBicycle(
    val id: Int,
    val name: String,
    val sellingPrice: Int?,
    val picture: Bitmap?,
    val wheelSize: Double
)