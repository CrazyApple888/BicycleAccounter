package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.model.State
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject


class EditBicycleStateUseCase @Inject constructor(
    private val repository: BicycleRepository
) {
    suspend operator fun invoke(bicycleId: Int, newState: State) =
        repository.updateBicycleState(bicycleId, newState)
}
