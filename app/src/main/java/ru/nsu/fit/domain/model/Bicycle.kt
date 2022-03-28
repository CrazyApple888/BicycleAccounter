package ru.nsu.fit.domain.model

import android.graphics.Bitmap

data class Bicycle(
    val id: Int = 0,
    val name: String,
    val purchasePrice: Int,
    val sellingPrice: Int? = null,
    val description: String? = null,
    val picture: Bitmap? = null,
    val type: Type,
    val state: State,
    val wheelSize: WheelSize,
    val color: Color,
    val issues: List<Issue> = emptyList()
) : BaseModel()
