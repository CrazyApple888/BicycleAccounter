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
    suspend fun insertCustomerItem(customerDto: CustomerDto): Long

    suspend fun insertCustomerItem(name: String, phone: String): Long {
        return insertCustomerItem(CustomerDto(id = 0, name = name, phone = phone))
    }

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

    @Query("SELECT DISTINCT * FROM customers WHERE customers.name LIKE :name AND customers.phone LIKE :phone LIMIT 1")
    fun selectFirstCustomerByNameAndPhone(name: String, phone: String): CustomerDto?
}