package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleBicycle

interface BicycleRepository {
    suspend fun getAllBicycles(): Flow<Result<List<SimpleBicycle>>>
    suspend fun getBicycleById(id: Int): Flow<Result<Bicycle>>
    suspend fun insertBicycle(bicycle: Bicycle): Result<Int>
}