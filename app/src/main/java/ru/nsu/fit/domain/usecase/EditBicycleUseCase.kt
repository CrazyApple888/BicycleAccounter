package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.model.Issue
import ru.nsu.fit.domain.model.State
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class EditBicycleUseCase @Inject constructor(
    private val repository: BicycleRepository
) {
    suspend fun updateSellingPrice(bicycleId: Int, sellingPrice: Int) =
        repository.updateBicycleSellingPrice(bicycleId, sellingPrice)

    suspend fun addIssue(bicycleId: Int, issue: Issue) =
        repository.addIssueToBicycle(bicycleId, issue)

    suspend fun updateBicycleState(bicycleId: Int, newState: State) =
        repository.updateBicycleState(bicycleId, newState)
}