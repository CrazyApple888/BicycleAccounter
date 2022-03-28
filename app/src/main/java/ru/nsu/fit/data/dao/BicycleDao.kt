package ru.nsu.fit.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.BicycleAllSpecs
import ru.nsu.fit.data.model.BicycleDto
import ru.nsu.fit.data.model.BicycleSimplified

@Dao
interface BicycleDao {
    @Query("SELECT * FROM bicycles")
    fun selectBicycleAll(): Flow<List<BicycleDto>>

    @Query(
        "SELECT bikeId, name, sellingPrice, picture, sizeInches AS wheelSize FROM bicycles " +
                "INNER JOIN wheel_sizes ON wheel_sizes.sizeId = bicycles.wheelSizeIdRef"
    )
    fun selectSimplifiedBicycleAll(): Flow<List<BicycleSimplified>>

    @Query(
        "SELECT bikeId, name, sellingPrice, picture, sizeInches AS wheelSize FROM bicycles " +
                "INNER JOIN wheel_sizes ON wheel_sizes.sizeId = bicycles.wheelSizeIdRef " +
                "WHERE bicycles.stateIdRef = :stateId"
    )
    fun selectSimplifiedBicycleByState(stateId: Int): Flow<List<BicycleSimplified>>

    @Query("SELECT DISTINCT * FROM bicycles WHERE bikeId = :id")
    fun selectBicycleById(id: Int): Flow<BicycleDto?>

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query(
        "SELECT * FROM (SELECT DISTINCT * FROM bicycles WHERE bicycles.bikeId = :id) AS bicycle " +
                "INNER JOIN colors ON bicycle.colorIdRef = colors.colorId " +
                "INNER JOIN wheel_sizes ON bicycle.wheelSizeIdRef = wheel_sizes.sizeId " +
                "INNER JOIN bicycle_types ON bicycle.typeIdRef = bicycle_types.typeId " +
                "INNER JOIN bicycle_states ON  bicycle.stateIdRef = bicycle_states.stateId "
    )
    fun selectBicycleWithSpecsById(id: Int): Flow<BicycleAllSpecs?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBicycleItem(bicycleDto: BicycleDto)

    @Query("UPDATE bicycles SET stateIdRef = :stateId WHERE bikeId = :bikeId")
    suspend fun updateBicycleStateById(bikeId: Int, stateId: Int)

    @Query("UPDATE bicycles SET sellingPrice = :sellingPrice WHERE bikeId = :bikeId")
    suspend fun updateBicycleSellingPriceByID(bikeId: Int, sellingPrice: Int)
}
