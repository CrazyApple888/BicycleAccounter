package ru.nsu.fit.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Bicycle
import ru.nsu.fit.data.model.BicycleAllSpecs
import ru.nsu.fit.data.model.BicycleSimplified

@Dao
interface BicycleDao {
    @Query("SELECT * FROM bicycles")
    fun selectBicycleAll(): Flow<List<Bicycle>>

    @Query(
        "SELECT bikeId, name, sellingPrice, picture, sizeInches AS wheelSize FROM bicycles " +
                "INNER JOIN wheel_sizes ON wheel_sizes.sizeId = bicycles.wheelSizeId"
    )
    fun selectSimplifiedBicycleAll(): Flow<List<BicycleSimplified>>

    @Query(
        "SELECT bikeId, name, sellingPrice, picture, sizeInches AS wheelSize FROM bicycles " +
                "INNER JOIN wheel_sizes ON wheel_sizes.sizeId = bicycles.wheelSizeId " +
                "WHERE bicycles.stateId = :stateId"
    )
    fun selectSimplifiedBicycleByState(stateId: Int): Flow<List<BicycleSimplified>>

    @Query("SELECT DISTINCT * FROM bicycles WHERE bikeId = :id")
    fun selectBicycleById(id: Int): Flow<Bicycle>

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query(
        "SELECT * FROM (SELECT * FROM bicycles WHERE bicycles.bikeId = :id ) AS bicycle " +
                "INNER JOIN colors ON bicycle.colorId = colors.colorId " +
                "INNER JOIN wheel_sizes ON bicycle.wheelSizeId = wheel_sizes.sizeId " +
                "INNER JOIN bicycle_types ON bicycle.typeId = bicycle_types.typeId " +
                "INNER JOIN bicycle_states ON  bicycle.stateId = bicycle_states.stateId "
    )
    fun selectBicycleWithSpecsById(id: Int): Flow<BicycleAllSpecs>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBicycleItem(bicycle: Bicycle)


    @Query("UPDATE bicycles SET stateId = :stateId WHERE bikeId = :bikeId")
    suspend fun updateBicycleStateById(bikeId: Int, stateId: Int)

    @Query("UPDATE bicycles SET sellingPrice = :sellingPrice WHERE bikeId = :bikeId")
    suspend fun updateBicycleSellingPriceByID(bikeId: Int, sellingPrice: Int)
}
