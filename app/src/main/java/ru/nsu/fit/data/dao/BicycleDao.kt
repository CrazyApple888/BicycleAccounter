package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Bicycle
import ru.nsu.fit.data.model.BicycleAllSpecs
import ru.nsu.fit.data.model.BicycleSimplified

@Dao
interface BicycleDao {
    @Query("SELECT * FROM bicycles")
    fun selectAllBicycles(): Flow<List<Bicycle>>

    @Query("SELECT * FROM bicycles")
    fun selectAllSimplifiedBicycles(): Flow<List<BicycleSimplified>>

    @Query("SELECT * FROM bicycles WHERE bikeId = :id")
    fun selectBicycleById(id: Int): Flow<Bicycle>

    @Query(
        "SELECT * FROM (SELECT * FROM bicycles WHERE bicycles.bikeId = :id ) AS bicycle " +
                "INNER JOIN colors ON bicycle.colorId = colors.colorId " +
                "INNER JOIN wheel_sizes ON bicycle.wheelSizeId = wheel_sizes.sizeId " +
                "INNER JOIN bicycle_types ON bicycle.typeId = bicycle_types.typeId " +
                "INNER JOIN bicycle_states ON  bicycle.stateId = bicycle_states.stateId "
    )
    fun selectBicycleWithSpecsById(id: Int): Flow<BicycleAllSpecs>
}
