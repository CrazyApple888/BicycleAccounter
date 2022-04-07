package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Color
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.ColorRepository
import javax.inject.Inject

class GetAllColorsUseCase @Inject constructor(
    private val colorRepository: ColorRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Color>>> = colorRepository.getColorAll()
}