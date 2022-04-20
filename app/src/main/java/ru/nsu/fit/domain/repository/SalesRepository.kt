package ru.nsu.fit.domain.repository

import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result

interface SalesRepository {

    suspend fun addCustomerAndUpdateBicycleState(
        bikeId: Int,
        price: Double,
        customer: Customer
    ): Result<*>

}