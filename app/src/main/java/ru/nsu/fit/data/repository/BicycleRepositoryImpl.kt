package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.BicycleAllSpecsDto
import ru.nsu.fit.data.model.BicycleSimplifiedDto
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class BicycleRepositoryImpl @Inject constructor(
    private val bicycleDao: BicycleDao,
    private val simpleBicycleMapperDto: Mapper<SimpleBicycle, BicycleSimplifiedDto>,
    private val bicycleAllSpecsMapperDto: Mapper<Bicycle, BicycleAllSpecsDto>,
) : BicycleRepository {
    override suspend fun getAllBicycles(): Flow<Result<List<SimpleBicycle>>> =
        withContext(Dispatchers.IO) {
            bicycleDao.selectSimplifiedBicycleAll().map {
                if (it.isEmpty()) {
                    Result.Failure(message = "", result = null)
                } else {
                    Result.Success(result = it.map(simpleBicycleMapperDto::toDomain))
                }
            }

        }

    override suspend fun getBicycleById(id: Int): Flow<Result<Bicycle>> =
        withContext(Dispatchers.IO) {
            bicycleDao.selectBicycleWithSpecsById(id).map {
                if (null == it) {
                    Result.Failure(message = "", result = null)
                } else {
                    Result.Success(result = bicycleAllSpecsMapperDto.toDomain(it))
                }
            }
        }

    override suspend fun insertBicycle(bicycle: Bicycle, options: Map<String, Int>): Result<Int> =
        withContext(Dispatchers.IO) {
            val id = bicycleDao.insertBicycleItem(
                bicycleAllSpecsMapperDto.toData(bicycle, options).bicycleDto
            ).toInt()

            if (0 == id) {
                Result.Failure(message = "", result = null)
            } else {
                Result.Success(result = id)
            }
        }
}