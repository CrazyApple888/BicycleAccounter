package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val phone: String
)
