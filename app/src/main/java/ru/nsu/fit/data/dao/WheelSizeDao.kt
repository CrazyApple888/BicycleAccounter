package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Color
import ru.nsu.fit.data.model.WheelSize

@Dao
interface WheelSizeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWheelSizeItem(wheelSize: WheelSize)

    suspend fun insertWheelSize(size: Double) {
        insertWheelSizeItem(WheelSize(sizeId = 0, sizeInches = size))
    }


    @Query("SELECT * FROM wheel_sizes")
    fun selectWheelSizeAll(): Flow<List<WheelSize>>

    @Query("SELECT DISTINCT * FROM wheel_sizes WHERE wheel_sizes.sizeId = :id")
    fun selectWheelSizeById(id: Int): Flow<WheelSize>
}