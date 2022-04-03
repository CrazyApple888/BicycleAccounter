package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.ColorDto


@Dao
interface ColorDao {
    @Query("SELECT * FROM colors ORDER BY colorName")
    fun selectColorAll(): Flow<List<ColorDto>>

    @Query("SELECT DISTINCT colorId FROM colors WHERE colors.colorName = :name")
    fun selectIdByName(name: String): Int?

    @Query("SELECT DISTINCT * FROM colors WHERE colors.colorId = :id")
    fun selectColorById(id: Int): Flow<ColorDto?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertColorItem(newColorDto: ColorDto): Long
}