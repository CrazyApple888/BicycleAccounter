package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Customer

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCustomerItem(customer: Customer)

    suspend fun insertCustomerItem(name: String, phone: String) {
        insertCustomerItem(Customer(id = 0, name = name, phone = phone))
    }

    @Query("SELECT * FROM customers")
    fun selectCustomerAll(): Flow<List<Customer>>

    @Query("SELECT DISTINCT * FROM customers WHERE customers.id = :id")
    fun selectCustomerById(id: Int): Flow<Customer?>
}