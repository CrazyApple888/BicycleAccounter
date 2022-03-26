package ru.nsu.fit.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bicycles")
data class BicycleDto(
    @PrimaryKey(autoGenerate = true) val bikeId: Int = 0,
    val name: String,
    val purchasePrice: Int,
    val sellingPrice: Int?,
    val description: String?,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val picture: Bitmap?,

    //references
    val typeIdRef: Int,
    val stateIdRef: Int,
    val wheelSizeIdRef: Int,
    val colorIdRef: Int,
)