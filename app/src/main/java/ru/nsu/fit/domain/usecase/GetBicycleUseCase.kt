package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class GetBicycleUseCase @Inject constructor(
    private val repository: BicycleRepository
) {
    suspend operator fun invoke(id: Int): Flow<Result<Bicycle>> = repository.getBicycleById(id)
}