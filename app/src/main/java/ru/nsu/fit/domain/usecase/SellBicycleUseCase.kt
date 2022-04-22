package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.SalesManager
import javax.inject.Inject

class SellBicycleUseCase @Inject constructor(
    private val manager: SalesManager
) {
    suspend operator fun invoke(
        bikeId: Int,
        price: Double,
        customerName: String,
        customerPhone: String
    ): Result<*> = manager.addCustomerAndUpdateBicycleState(
        bikeId,
        price,
        Customer(customerName, customerPhone)
    )

}