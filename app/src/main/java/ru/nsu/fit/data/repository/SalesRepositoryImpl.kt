package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.dao.BicycleStateDao
import ru.nsu.fit.data.dao.CustomerDao
import ru.nsu.fit.data.dao.SalesDao
import ru.nsu.fit.data.model.StateDto
import ru.nsu.fit.data.model.TransactionFailure
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.SalesRepository
import java.util.*
import javax.inject.Inject

class SalesRepositoryImpl @Inject constructor(
    private val salesDao: SalesDao,
    private val customerDao: CustomerDao,
    private val bicycleDao: BicycleDao,
    private val stateDao: BicycleStateDao
) : SalesRepository {
    override suspend fun addCustomerAndUpdateBicycleState(
        bikeId: Int,
        price: Double,
        customer: Customer
    ): Result<*> = withContext(Dispatchers.IO) {
        val customerId =
            customerDao.selectFirstCustomerByNameAndPhone(customer.name, customer.phone)?.id
                ?: customerDao.insertCustomerItem(customer.name, customer.phone).toInt()
        if (customerId == TransactionFailure.TRANSACTION_REJECTED) {
            return@withContext Result.Failure("Unable to insert customer")
        }
        salesDao.insertSaleItem(
            bikeId,
            Calendar.getInstance(),
            customerId,
            price.toLong()
        )
        val stateId = stateDao.selectIdByName(StateDto.SOLD.stateName)
            ?: return@withContext Result.Failure<Any>("Unable to get state id for state ${StateDto.SOLD}")
        bicycleDao.updateBicycleStateById(bikeId, stateId)
        Result.Success()
    }
}