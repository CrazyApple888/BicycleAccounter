package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.BicycleStateDto

@Dao
interface BicycleStateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBicycleStateItem(bicycleStateDto: BicycleStateDto): Long

    @Query("SELECT DISTINCT stateId FROM bicycle_states WHERE bicycle_states.stateName = :name")
    fun selectIdByName(name: String): Int?

    @Query("SELECT * FROM bicycle_states ORDER BY stateName")
    fun selectBicycleStateAll(): Flow<List<BicycleStateDto>>
}