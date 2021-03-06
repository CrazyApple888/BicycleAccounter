package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.SaleDetailedDto
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
    fun selectSaleAllWithItems(): Flow<List<SaleWithItemsDto>>

    @Query("SELECT * FROM sales WHERE sales.bicycleId = :id")
    fun selectDetailedSaleById(id: Int): Flow<SaleDetailedDto?>
}