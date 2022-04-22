package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Sale

interface SalesManager {

    suspend fun addCustomerAndUpdateBicycleState(
        bikeId: Int,
        price: Double,
        customer: Customer
    ): Result<*>

    suspend fun getAllSales():  Flow<Result<List<Sale>>>

}