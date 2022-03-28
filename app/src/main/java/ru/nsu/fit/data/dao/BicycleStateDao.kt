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
    suspend fun insertBicycleStateItem(bicycleStateDto: BicycleStateDto)

    suspend fun insertBicycleStateItem(stateName: String) {
        insertBicycleStateItem(BicycleStateDto(stateId = 0, stateName = stateName))
    }

    @Query("SELECT * FROM bicycle_states")
    fun selectBicycleStatesAll(): Flow<List<BicycleStateDto>>

    @Query("SELECT * FROM bicycle_states WHERE bicycle_states.stateId = :id")
    fun selectBicycleStateById(id: Int): Flow<BicycleStateDto?>
}