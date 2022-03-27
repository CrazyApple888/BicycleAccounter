package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorDto(
    @PrimaryKey(autoGenerate = true) val colorId: Int = 0,
    val colorName: String
)
