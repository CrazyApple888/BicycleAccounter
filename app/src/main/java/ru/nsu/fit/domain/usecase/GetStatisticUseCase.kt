package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.repository.StatisticRepository
import javax.inject.Inject

class GetStatisticUseCase @Inject constructor(
    private val statsRepository: StatisticRepository
) {
    suspend operator fun invoke() = statsRepository.getStatistic()
}