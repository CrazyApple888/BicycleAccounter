package ru.nsu.fit.data.typeconverter

import androidx.room.TypeConverter
import ru.nsu.fit.data.model.StateDto

class StateTypeConverter {

    @TypeConverter
    fun toState(name: String): StateDto =
        StateDto.forName(name)

    @TypeConverter
    fun toString(state: StateDto): String = state.name

}