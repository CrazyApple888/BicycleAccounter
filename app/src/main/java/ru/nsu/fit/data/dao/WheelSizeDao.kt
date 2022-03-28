package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.WheelSizeDto

@Dao
interface WheelSizeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWheelSizeItem(wheelSizeDto: WheelSizeDto): Long

    @Query("SELECT * FROM wheel_sizes ORDER BY sizeInches")
    fun selectWheelSizeAll(): Flow<List<WheelSizeDto>>

    @Query("SELECT DISTINCT * FROM wheel_sizes WHERE wheel_sizes.sizeId = :id")
    fun selectWheelSizeById(id: Int): Flow<WheelSizeDto?>
}