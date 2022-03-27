package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class GetAllBicyclesUseCase @Inject constructor(
    private val repository: BicycleRepository
) {
    suspend operator fun invoke(): Flow<List<SimpleBicycle>> = repository.getAllBicycles()
}