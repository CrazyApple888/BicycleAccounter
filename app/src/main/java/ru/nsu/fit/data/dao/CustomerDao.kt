package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.CustomerDto
import ru.nsu.fit.data.model.CustomerSimplifiedDto

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCustomerItem(customerDto: CustomerDto)

    @Query("SELECT * FROM customers")
    fun selectCustomerAll(): Flow<List<CustomerDto>>

    @Query(
        "SELECT id, name, phone, MAX(saleDate) as lastTrade " +
                "FROM customers " +
                "LEFT JOIN sales ON sales.customerId = customers.id " +
                "GROUP BY id"
    )
    fun selectCustomerSimplifiedAll(): Flow<List<CustomerSimplifiedDto>>

    @Query("SELECT DISTINCT * FROM customers WHERE customers.id = :id")
    fun selectCustomerById(id: Int): Flow<CustomerDto?>

//    @RewriteQueriesToDropUnusedColumns
//    @Query(
//        "WITH client AS (SELECT DISTINCT * FROM customers WHERE customers.id = :id) " +
//                "SELECT DISTINCT * " +
//                "FROM (SELECT * FROM sales INNER JOIN bicycles ON sales.bicycleId = bicycles.bikeId) as sold_bikes " +
//                "INNER JOIN client ON client.id = sold_bikes.customerId"
//    )
//    fun selectCustomerDetailed(id: Int): Flow<DetailedCustomerDto>
}
