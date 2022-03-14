package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Sale

@Dao
interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSaleItem(sale: Sale)

    suspend fun insertSaleItem(bicycleId: Int, saleDate: String, customerId: Int, finalCost: Long) {
        insertSaleItem(
            Sale(
                bicycleId = bicycleId,
                saleDate = saleDate,
                customerId = customerId,
                finalCost = finalCost
            )
        )
    }

    @Query("SELECT * FROM sales")
    fun selectSaleAll(): Flow<List<Sale>>

    @Query("SELECT DISTINCT * FROM sales WHERE sales.bicycleId = :bikeId")
    fun selectSaleByBikeId(bikeId: Int): Flow<Sale>
}