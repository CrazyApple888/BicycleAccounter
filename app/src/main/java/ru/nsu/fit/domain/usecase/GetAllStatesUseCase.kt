package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.State
import ru.nsu.fit.domain.repository.StateRepository
import javax.inject.Inject

class GetAllStatesUseCase @Inject constructor(
    private val stateRepository: StateRepository
) {
    suspend operator fun invoke(): Flow<Result<List<State>>> = stateRepository.getStateAll()

}