package ru.nsu.fit.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.BicycleAllSpecsDto
import ru.nsu.fit.data.model.BicycleSimplifiedDto
import ru.nsu.fit.data.model.TransactionFailure
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.LoggingTags
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.repository.*
import javax.inject.Inject

class BicycleRepositoryImpl @Inject constructor(
    private val bicycleDao: BicycleDao,
    private val simpleBicycleMapperDto: Mapper<SimpleBicycle, BicycleSimplifiedDto>,
    private val bicycleAllSpecsMapperDto: Mapper<Bicycle, BicycleAllSpecsDto>,
    private val colorRepository: ColorRepository,
    private val stateRepository: StateRepository,
    private val wheelSizeRepository: WheelSizeRepository,
    private val typeRepository: TypeRepository
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

    override suspend fun insertBicycle(bicycle: Bicycle): Result<Int> =
        withContext(Dispatchers.IO) {
            val colorId = async {
                colorRepository.insertColorItem(bicycle.color).successOrNull { message, _ ->
                    Log.e(
                        LoggingTags.USECASE,
                        "Unable to get wheel size id, error message: $message"
                    )
                } ?: 0
            }
            val typeId = async {
                typeRepository.insertTypeItem(bicycle.type).successOrNull { message, _ ->
                    Log.e(
                        LoggingTags.USECASE,
                        "Unable to get wheel size id, error message: $message"
                    )
                } ?: 0
            }
            val stateId = async {
                stateRepository.insertStateItem(bicycle.state).successOrNull { message, _ ->
                    Log.e(
                        LoggingTags.USECASE,
                        "Unable to get bicycle state id, error message: $message"
                    )
                } ?: 0
            }
            val sizeId = async {
                wheelSizeRepository.insertSizeItem(bicycle.wheelSize)
                    .successOrNull { message, _ ->
                        Log.e(
                            LoggingTags.USECASE,
                            "Unable to get wheel size id, error message: $message"
                        )
                    } ?: 0
            }

            if (0 == sizeId.await() || 0 == colorId.await() ||
                0 == sizeId.await() || 0 == sizeId.await()
            ) {
                Result.Failure(message = "Failed to get id for wheel size, color, type or state of bicycle, check logs for details")
            } else {
                val bicycleId = bicycleDao.insertBicycleItem(
                    bicycleAllSpecsMapperDto.toData(
                        bicycle,
                        options = mapOf(
                            "colorId" to colorId.await(),
                            "typeId" to typeId.await(),
                            "sizeId" to sizeId.await(),
                            "stateId" to stateId.await()
                        )
                    ).bicycleDto
                ).toInt()
                if (TransactionFailure.ALREADY_EXISTS.ordinal != bicycleId && TransactionFailure.TRANSACTION_REJECTED.ordinal != bicycleId) {
                    Result.Success(result = bicycleId)
                } else {
                    Result.Failure(
                        message = "Bicycle insertion failed, but props inserted properly, check bicycle repository",
                        result = null
                    )
                }
            }
        }
}