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
    fun selectAllAvailableColors(): Flow<List<Color>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewColor(newColor: Color)
}