package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.SaleDto
import ru.nsu.fit.data.model.SaleWithItemsDto
import java.util.*

@Dao
interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSaleItem(saleDto: SaleDto)

    suspend fun insertSaleItem(
        bicycleId: Int,
        saleDate: Calendar,
        customerId: Int,
        finalCost: Long
    ) {
        insertSaleItem(
            SaleDto(
                bicycleId = bicycleId,
                saleDate = saleDate,
                customerId = customerId,
                finalCost = finalCost
            )
        )
    }

    @Query("SELECT * FROM sales")
    fun selectSaleAll(): Flow<List<SaleDto>>

    @Query("SELECT DISTINCT * FROM sales WHERE sales.bicycleId = :bikeId")
    fun selectSaleByBikeId(bikeId: Int): Flow<SaleDto?>

//    @Query(
//        """SELECT
//            bicycles.bikeId AS bike_bikeId,
//            bicycles.name as bike_name,
//            bicycles.sellingPrice,
//            bicycles.picture,
//            wheel_sizes.sizeInches AS bike_wheelSize,
//            sales.saleDate AS date,
//            sales.saleDate AS warrantyEndDate,
//            customers.id AS customer_id,
//            customers.name AS customer_name,
//            customers.phone AS customer_phone,
//            sales.finalCost
//        FROM sales
//        INNER JOIN bicycles ON sales.bicycleId = bicycles.bikeId
//        INNER JOIN customers ON sales.customerId = customers.id
//        INNER JOIN wheel_sizes ON bicycles.wheelSizeIdRef = wheel_sizes.sizeId
//    """)
//    fun selectSaleAllWithItems(): Flow<List<SaleWithItemsDto>>

    @Query("SELECT * FROM sales")
    fun selectSaleAllWithItems(): Flow<List<SaleWithItemsDto>>
}