package ru.nsu.fit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bicycles")
data class Bicycle(
    @PrimaryKey(autoGenerate = true) val bikeId: Long,
    val name: String,
    val purchasePrice: Int,
    val sellingPrice: Int?,
    val description: String?,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val picture: ByteArray,

    //references
    val typeId: Int,
    val stateId: Int,
    val wheelSizeId: Int,
    val colorId: Int,
)