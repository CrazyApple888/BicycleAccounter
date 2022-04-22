package ru.nsu.fit.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.*

@Dao
interface BicycleDao {
    @Query("SELECT * FROM bicycles")
    fun selectBicycleAll(): Flow<List<BicycleDto>>

    @Query(
        "SELECT * FROM bicycles " +
                "INNER JOIN wheel_sizes ON wheel_sizes.sizeId = bicycles.wheelSizeIdRef " +
                "INNER JOIN bicycle_states ON bicycle_states.stateId = bicycles.stateIdRef " +
                "WHERE stateName NOT LIKE :rejectedState"
    )
    fun selectSimplifiedBicycleAll(rejectedState: String = StateDto.SOLD.stateName): Flow<List<BicycleSimplifiedDto>>

    @Query(
        "SELECT * FROM bicycles " +
                "INNER JOIN wheel_sizes ON wheel_sizes.sizeId = bicycles.wheelSizeIdRef " +
                "WHERE bicycles.stateIdRef = :stateId"
    )
    fun selectSimplifiedBicycleByState(stateId: Int): Flow<List<BicycleSimplifiedDto>>

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
    fun selectBicycleWithSpecsById(id: Int): Flow<BicycleAllSpecsDto?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBicycleItem(bicycleDto: BicycleDto): Long

    @Query("UPDATE bicycles SET stateIdRef = :stateId WHERE bikeId = :bikeId")
    suspend fun updateBicycleStateById(bikeId: Int, stateId: Int)

    @Query("UPDATE bicycles SET sellingPrice = :sellingPrice WHERE bikeId = :bikeId")
    suspend fun updateBicycleSellingPriceByID(bikeId: Int, sellingPrice: Int)

    @Query("SELECT * FROM sold_bicycles WHERE customerId = :id")
    suspend fun selectSoldBicyclesByCustomerId(id: Int): List<SoldBicycleDto>
}
