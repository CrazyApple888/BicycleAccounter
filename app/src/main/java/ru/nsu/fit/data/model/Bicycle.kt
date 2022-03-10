package ru.nsu.fit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bicycle(
    @PrimaryKey(autoGenerate = true) val bikeId: Long,
    val name: String,
    val purchasePrice: Int,
    val sellingPrice: Int?,
    val description: String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val picture: ByteArray?,

    //references
    val type: Long,
    val state: Long,
    val wheelSizeId: Long,
    val colorId: Long,
)