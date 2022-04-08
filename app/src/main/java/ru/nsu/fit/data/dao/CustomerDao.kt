package ru.nsu.fit.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.CustomerDto
import ru.nsu.fit.data.model.CustomerSimplified
import ru.nsu.fit.data.model.DetailedCustomerDto

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCustomerItem(customerDto: CustomerDto)

    @Query("SELECT * FROM customers")
    fun selectCustomerAll(): Flow<List<CustomerDto>>

    @Query("SELECT * FROM customers")
    fun selectCustomerSimplifiedAll(): Flow<List<CustomerSimplified>>

    @Query("SELECT DISTINCT * FROM customers WHERE customers.id = :id")
    fun selectCustomerById(id: Int): Flow<CustomerDto?>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        "WITH client AS (SELECT DISTINCT * FROM customers WHERE customers.id = :id) " +
                "SELECT DISTINCT * " +
                "FROM (SELECT * FROM sales INNER JOIN bicycles ON sales.bicycleId = bicycles.bikeId) as sold_bikes " +
                "INNER JOIN client ON client.id = sold_bikes.customerId"
    )
    fun selectCustomerDetailed(id: Int): Flow<DetailedCustomerDto>
}
