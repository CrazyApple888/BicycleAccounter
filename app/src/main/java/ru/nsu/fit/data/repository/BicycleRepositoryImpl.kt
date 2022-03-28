package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.BicycleAllSpecs
import ru.nsu.fit.data.model.BicycleSimplified
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class BicycleRepositoryImpl @Inject constructor(
    private val bicycleDao: BicycleDao,
    private val simpleBicycleMapper: Mapper<SimpleBicycle, BicycleSimplified>,
    private val bicycleAllSpecsMapper: Mapper<Bicycle, BicycleAllSpecs>
) : BicycleRepository {
    override suspend fun getAllBicycles(): Flow<Result<List<SimpleBicycle>>> =
        withContext(Dispatchers.IO) {
            bicycleDao.selectSimplifiedBicycleAll().map {
                if (it.isEmpty()) {
                    Result.Failure(message = "", result = null)
                } else {
                    Result.Success(result = it.map(simpleBicycleMapper::toDomain))
                }
            }

        }

    override suspend fun getBicycleById(id: Int): Flow<Result<Bicycle>> =
        withContext(Dispatchers.IO) {
            bicycleDao.selectBicycleWithSpecsById(id).map {
                if (null == it) {
                    Result.Failure(message = "", result = null)
                } else {
                    Result.Success(result = bicycleAllSpecsMapper.toDomain(it))
                }
            }
        }
}