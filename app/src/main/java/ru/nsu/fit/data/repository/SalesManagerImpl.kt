package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.dao.BicycleStateDao
import ru.nsu.fit.data.dao.SalesDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.mapper.mapModels
import ru.nsu.fit.data.model.SaleWithItemsDto
import ru.nsu.fit.data.model.StateDto
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.domain.repository.CustomerRepository
import ru.nsu.fit.domain.repository.SalesManager
import java.util.*
import javax.inject.Inject

class SalesManagerImpl @Inject constructor(
    private val salesDao: SalesDao,
    private val customerRepository: CustomerRepository,
    private val bicycleDao: BicycleDao,
    private val stateDao: BicycleStateDao,
    private val saleMapper: Mapper<Sale, SaleWithItemsDto>
) : SalesManager {

    override suspend fun addCustomerAndUpdateBicycleState(
        bikeId: Int,
        price: Double,
        customer: Customer
    ): Result<*> = withContext(Dispatchers.IO) {
        when (val customerId = customerRepository.insertCustomer(customer)) {
            is Result.Failure -> Result.Failure("Unable to insert customer")
            is Result.Success -> {
                salesDao.insertSaleItem(
                    bikeId,
                    Calendar.getInstance(),
                    customerId.result!!,
                    price.toLong()
                )
                val stateId = stateDao.selectIdByName(StateDto.SOLD.stateName)
                    ?: return@withContext Result.Failure<Any>("Unable to get state id for state ${StateDto.SOLD}")
                bicycleDao.updateBicycleStateById(bikeId, stateId)
                return@withContext Result.Success<Any>()
            }
        }
    }

    override suspend fun getAllSales(): Flow<Result<List<Sale>>> =
        salesDao.selectSaleAllWithItems()
            .mapModels(saleMapper::toDomain)
            .map { list -> Result.Success(list) }
            .flowOn(Dispatchers.IO)

}