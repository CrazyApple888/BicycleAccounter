package ru.nsu.fit.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.*
import ru.nsu.fit.domain.repository.*
import javax.inject.Inject

class AddBicycleUseCase @Inject constructor(
    private val bicycleRepository: BicycleRepository,
    private val colorRepository: ColorRepository,
    private val typeRepository: TypeRepository,
    private val stateRepository: StateRepository,
    private val wheelSizeRepository: WheelSizeRepository
) {
    suspend fun getColors(): Flow<Result<List<Color>>> = colorRepository.getColorAll()
    suspend fun getTypes(): Flow<Result<List<Type>>> = typeRepository.getTypeAll()
    suspend fun getStates(): Flow<Result<List<State>>> = stateRepository.getStateAll()
    suspend fun getWheelSizes(): Flow<Result<List<WheelSize>>> =
        wheelSizeRepository.getWheelSizeAll()


    suspend fun addBicycle(bicycle: Bicycle): Result<Int> {
        var idsSuccess = true
        val colorId =
            colorRepository.insertColorItem(bicycle.color).successOrNull { message, _ ->
                Log.e("Repository", "Unable to get wheel size id, error message: $message")
                idsSuccess = false
            } ?: 0
        val typeId = typeRepository.insertTypeItem(bicycle.type).successOrNull { message, _ ->
            Log.e("Repository", "Unable to get wheel size id, error message: $message")
            idsSuccess = false
        } ?: 0
        val stateId =
            stateRepository.insertStateItem(bicycle.state).successOrNull { message, _ ->
                Log.e("Repository", "Unable to get bicycle state id, error message: $message")
                idsSuccess = false
            } ?: 0
        val sizeId =
            wheelSizeRepository.insertSizeItem(bicycle.wheelSize).successOrNull { message, _ ->
                Log.e("Repository", "Unable to get wheel size id, error message: $message")
                idsSuccess = false
            } ?: 0

        return if (idsSuccess) {
            bicycleRepository.insertBicycle(
                bicycle,
                mapOf(
                    "colorId" to colorId,
                    "typeId" to typeId,
                    "stateId" to stateId,
                    "sizeId" to sizeId
                )
            )
        } else {
            Result.Failure(message = "Bicycle insertion failed")
        }
    }
}