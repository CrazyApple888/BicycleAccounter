package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.State
import ru.nsu.fit.domain.model.Type
import ru.nsu.fit.domain.model.WheelSize
import ru.nsu.fit.domain.repository.TypeRepository
import javax.inject.Inject

class GetAllTypesUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Type>>> = typeRepository.getTypeAll()
}