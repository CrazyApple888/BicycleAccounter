package ru.nsu.fit.data.repository

import ru.nsu.fit.data.dao.StatisticDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.StatisticDto
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Statistic
import ru.nsu.fit.domain.repository.StatisticRepository
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    private val statsDao: StatisticDao,
    private val statisticMapper: Mapper<Statistic, StatisticDto>
) : StatisticRepository {
    override suspend fun getStatistic(): Result<Statistic> {
        val dirtyProfit = statsDao.getDirtyProfitAll() ?: 0
        val issuesLosses = statsDao.getIssuesLossesAll() ?: 0
        val moneySpent = statsDao.getMoneySpent() ?: 0
        val bicyclesSold = statsDao.getSoldBicyclesCount() ?: 0

        val dto = StatisticDto(
            bicyclesSold = bicyclesSold,
            moneySpent = moneySpent,
            issuesLosses = issuesLosses,
            profitDirty = dirtyProfit
        )

        return Result.Success(result = statisticMapper.toDomain(dto))
    }
}