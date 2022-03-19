package ru.nsu.fit.data.model

data class BicycleSimplified(
    val name: String,
    val sellingPrice: Int?,
    val picture: ByteArray?,

    val wheelSize: Double
)