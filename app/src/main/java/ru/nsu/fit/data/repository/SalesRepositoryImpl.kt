package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.SalesRepository
import javax.inject.Inject

class SalesRepositoryImpl @Inject constructor() : SalesRepository {
    override suspend fun addCustomerAndUpdateBicycleState(
        bikeId: Int,
        price: Double,
        customer: Customer
    ): Result<*> = withContext(Dispatchers.IO) {
        Result.Success<Any>()
    }
}