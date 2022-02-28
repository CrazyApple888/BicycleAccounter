package ru.nsu.fit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Colors(
    @PrimaryKey(autoGenerate = true) val colorId: Long,
    val colorName: String
)
