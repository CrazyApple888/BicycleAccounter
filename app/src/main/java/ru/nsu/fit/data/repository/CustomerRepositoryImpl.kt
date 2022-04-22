package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.dao.CustomerDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.CustomerDetailedDto
import ru.nsu.fit.data.model.CustomerSimplifiedDto
import ru.nsu.fit.data.model.TransactionFailure
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.DetailedCustomer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleCustomer
import ru.nsu.fit.domain.repository.CustomerRepository
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val simpleCustomerMapper: Mapper<SimpleCustomer, CustomerSimplifiedDto>,
    private val detailedCustomerMapper: Mapper<DetailedCustomer, CustomerDetailedDto>,
    private val customerDao: CustomerDao,
    private val bicycleDao: BicycleDao
) : CustomerRepository {

    override suspend fun selectDetailedCustomer(id: Int): Flow<Result<DetailedCustomer>> =
        withContext(Dispatchers.IO) {
            customerDao.selectCustomerById(id).map { customer ->
                if (customer != null) {
                    val bicycles = bicycleDao.selectSoldBicyclesByCustomerId(customer.id)
                    Result.Success(
                        result = detailedCustomerMapper.toDomain(
                            CustomerDetailedDto(
                                customer = customer,
                                bikes = bicycles
                            )
                        )
                    )
                } else {
                    Result.Failure(message = "Customer with id: $id not found")
                }
            }
        }


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


    override suspend fun insertCustomer(customer: Customer): Result<Int> =
        withContext(Dispatchers.IO) {
            val customerId =
                customerDao.selectFirstCustomerByNameAndPhone(customer.name, customer.phone)?.id
                    ?: customerDao.insertCustomerItem(customer.name, customer.phone).toInt()

            if (customerId == TransactionFailure.TRANSACTION_REJECTED) {
                return@withContext Result.Failure("Unable to insert customer")
            }
            Result.Success(customerId)
        }
}