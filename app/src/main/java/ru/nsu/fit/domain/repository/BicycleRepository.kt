package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.*

interface BicycleRepository {
    suspend fun getAllBicycles(): Flow<Result<List<SimpleBicycle>>>
    suspend fun getBicycleById(id: Int): Flow<Result<Bicycle>>
    suspend fun insertBicycle(bicycle: Bicycle): Result<Int>
    suspend fun updateBicycleState(bicycleId: Int, state: State): Result<*>
    suspend fun addIssueToBicycle(bicycleId: Int, issue: Issue): Result<*>
    suspend fun updateBicycleSellingPrice(bicycleId: Int, newPrice: Int): Result<*>
}