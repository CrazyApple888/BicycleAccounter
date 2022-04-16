package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "customers", indices = [Index(value = ["phone"], unique = true)])
data class CustomerDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String
) : BaseDto()
