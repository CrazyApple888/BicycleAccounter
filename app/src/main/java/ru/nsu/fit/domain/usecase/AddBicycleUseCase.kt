package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.*
import ru.nsu.fit.domain.repository.*
import javax.inject.Inject

class AddBicycleUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
    private val typeRepository: TypeRepository,
    private val stateRepository: StateRepository,
    private val wheelSizeRepository: WheelSizeRepository,
    private val bicycleRepository: BicycleRepository
) {
    suspend fun getColors(): Flow<Result<List<Color>>> = colorRepository.getColorAll()
    suspend fun getTypes(): Flow<Result<List<Type>>> = typeRepository.getTypeAll()
    suspend fun getStates(): Flow<Result<List<State>>> = stateRepository.getStateAll()
    suspend fun getWheelSizes(): Flow<Result<List<WheelSize>>> =
        wheelSizeRepository.getWheelSizeAll()


    suspend fun addBicycle(bicycle: Bicycle): Result<Int> {
        return bicycleRepository.insertBicycle(bicycle)
    }
}