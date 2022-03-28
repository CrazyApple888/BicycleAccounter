package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "bicycle_states", indices = [Index(value = ["stateName"], unique = true)])
data class BicycleStateDto(
    @PrimaryKey(autoGenerate = true) val stateId: Int = 0,
    val stateName: String
) : BaseDto()