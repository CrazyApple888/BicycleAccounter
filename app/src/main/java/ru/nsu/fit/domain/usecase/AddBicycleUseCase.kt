package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class AddBicycleUseCase @Inject constructor(
    private val bicycleRepository: BicycleRepository
) {
    suspend operator fun invoke(bicycle: Bicycle): Result<Int> {
        return bicycleRepository.insertBicycle(bicycle)
    }
}