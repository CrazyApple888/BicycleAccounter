package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.mapper.mapModels
import ru.nsu.fit.data.model.BicycleSimplified
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class BicycleRepositoryImpl @Inject constructor(
    private val bicycleDao: BicycleDao,
    private val mapper: Mapper<SimpleBicycle, BicycleSimplified>
) : BicycleRepository {
    override suspend fun getAllBicycles(): Flow<List<SimpleBicycle>> = withContext(Dispatchers.IO) {
        bicycleDao.selectSimplifiedBicycleAll().mapModels(mapper::toDomain)
    }
}