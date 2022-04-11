package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.SalesRepository
import javax.inject.Inject

class SellBicycleUseCase @Inject constructor(
    private val repository: SalesRepository
) {

    suspend operator fun invoke(
        bikeId: Int,
        price: Double,
        customerName: String,
        customerPhone: String
    ): Result<*> = repository.addCustomerAndUpdateBicycleState(
        bikeId,
        price,
        Customer(customerName, customerPhone)
    )

}