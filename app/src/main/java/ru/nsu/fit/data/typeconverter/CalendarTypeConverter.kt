package ru.nsu.fit.data.typeconverter

import androidx.room.TypeConverter
import java.util.*

class CalendarTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            Calendar.getInstance().also { it.timeInMillis = value }
        }
    }

    @TypeConverter
    fun dateToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }
}
