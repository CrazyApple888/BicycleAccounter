package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.WheelSize
import ru.nsu.fit.domain.repository.WheelSizeRepository
import javax.inject.Inject

class GetAllWheelSizesUseCase @Inject constructor(
    private val wheelSizeRepository: WheelSizeRepository
) {
    suspend operator fun invoke(): Flow<Result<List<WheelSize>>> =
        wheelSizeRepository.getWheelSizeAll()
}