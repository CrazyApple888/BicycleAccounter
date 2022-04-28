package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class EditBicycleSellingUseCase @Inject constructor(
    private val repository: BicycleRepository
) {
    suspend operator fun invoke(bicycleId: Int, sellingPrice: Int) =
        repository.updateBicycleSellingPrice(bicycleId, sellingPrice)

}