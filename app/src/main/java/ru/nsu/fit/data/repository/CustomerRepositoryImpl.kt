package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.CustomerDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.CustomerDto
import ru.nsu.fit.data.model.CustomerSimplifiedDto
import ru.nsu.fit.data.model.TransactionStatus
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleCustomer
import ru.nsu.fit.domain.repository.CustomerRepository
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerMapper: Mapper<Customer, CustomerDto>,
    private val simpleCustomerMapper: Mapper<SimpleCustomer, CustomerSimplifiedDto>,
    private val customerDao: CustomerDao
) : CustomerRepository {
    override suspend fun selectCustomerAll(): Flow<Result<List<SimpleCustomer>>> =
        withContext(Dispatchers.IO) {
            customerDao.selectCustomerSimplifiedAll().map { customers ->
                if (customers.isEmpty()) {
                    Result.Failure(message = "Db query failed, check customer repository and daos")
                } else {
                    Result.Success(result = customers.map(simpleCustomerMapper::toDomain))
                }
            }
        }

    override suspend fun getCustomerById(id: Int): Result<Customer> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCustomer(customer: Customer): Result<Int> =
        withContext(Dispatchers.IO) {
            val customerId =
                customerDao.selectFirstCustomerByNameAndPhone(customer.name, customer.phone)?.id
                    ?: customerDao.insertCustomerItem(customer.name, customer.phone).toInt()

            if (customerId == TransactionStatus.TRANSACTION_REJECTED) {
                return@withContext Result.Failure("Unable to insert customer")
            }
            Result.Success(customerId)
        }
}