package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleCustomer
import ru.nsu.fit.domain.repository.CustomerRepository
import javax.inject.Inject

class GetSimpleCustomerAllUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(): Flow<Result<List<SimpleCustomer>>> =
        repository.selectCustomerAll()
}