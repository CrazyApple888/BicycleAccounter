package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.SimpleBicycle

interface BicycleRepository {
    suspend fun getAllBicycles(): Flow<List<SimpleBicycle>>
}