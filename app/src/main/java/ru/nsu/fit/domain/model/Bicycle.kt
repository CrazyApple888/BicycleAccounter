package ru.nsu.fit.domain.model

import android.graphics.Bitmap

data class Bicycle(
    val purchasePrice: Int,
    val sellingPrice: Int?,
    val description: String?,
    val picture: Bitmap,
    val type: Type,
    val state: State,
    val wheelSize: Double,
    val color: Color,
) {
}