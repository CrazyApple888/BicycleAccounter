package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject

class AddBicycleUseCase @Inject constructor(
    private val repository: BicycleRepository
) {

}