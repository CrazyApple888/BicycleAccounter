package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.WheelSize

interface WheelSizeRepository {
    suspend fun getWheelSizeAll(): Flow<Result<List<WheelSize>>>
    suspend fun insertSizeItem(wheelSize: WheelSize): Result<Int>
}