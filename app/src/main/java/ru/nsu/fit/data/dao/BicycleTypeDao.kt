package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.BicycleTypeDto

@Dao
interface BicycleTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBicycleTypeItem(bicycleTypeDto: BicycleTypeDto)

    suspend fun insertBicycleTypeItem(typeName: String) {
        insertBicycleTypeItem(BicycleTypeDto(typeId = 0, typeName = typeName))
    }

    @Query("SELECT * FROM bicycle_types")
    fun selectBicycleTypeAll(): Flow<List<BicycleTypeDto>>

    @Query("SELECT DISTINCT * FROM bicycle_types WHERE bicycle_types.typeId = :id")
    fun selectBicycleTypeById(id: Int): Flow<BicycleTypeDto?>
}