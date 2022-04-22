package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.repository.CustomerRepository
import javax.inject.Inject

class GetDetailedCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(id: Int) = customerRepository.selectDetailedCustomer(id)
}