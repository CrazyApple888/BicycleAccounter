package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.DetailedCustomer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleCustomer

interface CustomerRepository {
    suspend fun selectDetailedCustomer(id: Int): Flow<Result<DetailedCustomer>>
    suspend fun selectCustomerAll(): Flow<Result<List<SimpleCustomer>>>
    suspend fun insertCustomer(customer: Customer): Result<Int>
}