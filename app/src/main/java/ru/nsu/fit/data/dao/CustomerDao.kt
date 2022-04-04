package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.CustomerDto

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCustomerItem(customerDto: CustomerDto)

    @Query("SELECT * FROM customers")
    fun selectCustomerAll(): Flow<List<CustomerDto>>

    @
    @Query("SELECT DISTINCT * FROM customers WHERE customers.id = :id")
    fun selectCustomerById(id: Int): Flow<CustomerDto?>
}