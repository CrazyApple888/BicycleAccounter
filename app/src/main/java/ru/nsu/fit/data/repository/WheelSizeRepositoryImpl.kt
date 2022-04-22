package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.WheelSizeDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.TransactionStatus
import ru.nsu.fit.data.model.WheelSizeDto
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.WheelSize
import ru.nsu.fit.domain.repository.WheelSizeRepository
import javax.inject.Inject

class WheelSizeRepositoryImpl @Inject constructor(
    private val wheelSizeDao: WheelSizeDao,
    private val wheelSizeMapper: Mapper<WheelSize, WheelSizeDto>
) : WheelSizeRepository {
    override suspend fun getWheelSizeAll(): Flow<Result<List<WheelSize>>> =
        withContext(Dispatchers.IO) {
            wheelSizeDao.selectWheelSizeAll().map { sizes ->
                if (sizes.isEmpty()) {
                    Result.Failure(message = "Failed to load colors")
                } else {
                    Result.Success(result = sizes.map(wheelSizeMapper::toDomain))
                }
            }
        }

    override suspend fun insertSizeItem(wheelSize: WheelSize): Result<Int> =
        withContext(Dispatchers.IO) {
            val id =
                wheelSizeDao.selectIdBySize(wheelSize.diameter) ?: wheelSizeDao.insertWheelSizeItem(
                    wheelSizeMapper.toData(wheelSize)
                ).toInt()
            if (TransactionStatus.TRANSACTION_REJECTED != id) {
                Result.Success(result = id)
            } else {
                Result.Failure(
                    message = "Unable to insert new wheel size or retrieve id of existing"
                )
            }
        }
}