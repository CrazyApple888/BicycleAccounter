package ru.nsu.fit.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "bicycle_states", indices = [Index(value = ["stateName"], unique = true)])
data class BicycleStateDto(
    @PrimaryKey(autoGenerate = true) val stateId: Int = 0,
    val stateName: StateDto
) : BaseDto()

enum class StateDto(val stateName: String) {
    STORAGE("На складе"),
    ASSEMBLING("Сборка"),
    SELLING("Продается"),
    SOLD("Продан"),
    REFUND("После возврата"),
    FIXING("В ремонте"),
    FIXING_WARRANTY("В ремонте по гарантии");

    companion object {
        fun forName(name: String): StateDto =
            when (name) {
                "На складе" -> STORAGE
                "Сборка" -> ASSEMBLING
                "Продается" -> SELLING
                "Продан" -> SOLD
                "После возврата" -> REFUND
                "В ремонте" -> FIXING
                "В ремонте по гарантии" -> FIXING_WARRANTY
                else -> throw IllegalArgumentException()
            }
    }
}