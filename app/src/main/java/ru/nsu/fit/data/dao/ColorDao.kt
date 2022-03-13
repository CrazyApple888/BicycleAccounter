package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Color


@Dao
interface ColorDao {
    @Query("SELECT * FROM colors")
    fun selectColorAll(): Flow<List<Color>>

    @Query("SELECT DISTINCT * FROM colors WHERE colors.colorId = :id")
    fun selectColorById(id: Int): Flow<Color>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColorItem(newColor: Color)

    suspend fun insertColorItem(colorName: String) {
        insertColorItem(Color(colorId = 0, colorName = colorName))
    }
}