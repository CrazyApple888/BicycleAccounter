package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "colors", indices = [Index(value = ["colorName"], unique = true)])
data class ColorDto(
    @PrimaryKey(autoGenerate = true) val colorId: Int = 0,
    val colorName: String
) : BaseDto()
