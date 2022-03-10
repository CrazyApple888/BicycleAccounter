package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="bicycle_states")
data class BicycleState(
    @PrimaryKey(autoGenerate = true) val stateId: Long,
    val stateName: String
)