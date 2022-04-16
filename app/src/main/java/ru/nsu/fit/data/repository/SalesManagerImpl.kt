package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.dao.BicycleStateDao
import ru.nsu.fit.data.dao.SalesDao
import ru.nsu.fit.data.model.StateDto
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.CustomerRepository
import ru.nsu.fit.domain.repository.SalesRepository
import java.util.*
import javax.inject.Inject

class SalesManagerImpl @Inject constructor(
    private val salesDao: SalesDao,
    private val customerRepository: CustomerRepository,
    private val bicycleDao: BicycleDao,
    private val stateDao: BicycleStateDao
) : SalesRepository {
    override suspend fun addCustomerAndUpdateBicycleState(
        bikeId: Int,
        price: Double,
        customer: Customer
    ): Result<*>  {
        when (val customerId = customerRepository.insertCustomer(customer)) {
            is Result.Failure -> return Result.Failure<Any>("Unable to insert customer")
            is Result.Success -> {
                salesDao.insertSaleItem(
                    bikeId,
                    Calendar.getInstance(),
                    customerId.result!!,
                    price.toLong()
                )
                val stateId = stateDao.selectIdByName(StateDto.SOLD.stateName)
                    ?: return Result.Failure<Any>("Unable to get state id for state ${StateDto.SOLD}")
                bicycleDao.updateBicycleStateById(bikeId, stateId)
                return Result.Success<Any>()
            }
        }
    }
}