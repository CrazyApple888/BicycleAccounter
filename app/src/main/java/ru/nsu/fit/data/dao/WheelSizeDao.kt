package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.WheelSizeDto

@Dao
interface WheelSizeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWheelSizeItem(wheelSizeDto: WheelSizeDto): Long

    @Query("SELECT DISTINCT sizeId FROM wheel_sizes WHERE wheel_sizes.sizeInches BETWEEN (:size - 0.1) AND (:size + 0.1)")
    fun selectIdBySize(size: Double): Int?

    @Query("SELECT * FROM wheel_sizes ORDER BY sizeInches")
    fun selectWheelSizeAll(): Flow<List<WheelSizeDto>>
}