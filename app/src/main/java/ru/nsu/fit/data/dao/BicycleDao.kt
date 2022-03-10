package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Bicycle
import ru.nsu.fit.data.model.BicycleAllSpecs
import ru.nsu.fit.data.model.BicycleSimplified

@Dao
interface BicycleDao {
    @Query("select * from bicycles")
    fun selectAllBicycles(): Flow<List<Bicycle>>

    @Query("select * from bicycles")
    fun selectAllSimplifiedBicycles(): Flow<List<BicycleSimplified>>

    @Query("select * from bicycles where bikeId = :id")
    fun selectBicycleById(id: Int): Flow<Bicycle>

    //TODO("Make it optimized")

    @Query(
        "select * from bicycles " +
                "inner join colors on bicycles.colorId = colors.colorId " +
                "inner join wheel_sizes on bicycles.wheelSizeId = wheel_sizes.sizeId " +
                "inner join bicycle_types on bicycles.typeId = bicycle_types.typeId " +
                "inner join bicycle_states on  bicycles.stateId = bicycle_states.stateId " +
                "where bikeId = :id "
    )
    fun selectBicycleWithSpecsById(id: Int): Flow<BicycleAllSpecs>
}
