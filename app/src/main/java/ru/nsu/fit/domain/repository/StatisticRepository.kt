package ru.nsu.fit.domain.repository

import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Statistic

interface StatisticRepository {
    suspend fun getStatistic(): Result<Statistic>
}