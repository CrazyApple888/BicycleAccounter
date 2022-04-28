package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.StatisticDto
import ru.nsu.fit.domain.model.Statistic
import javax.inject.Inject

class StatisticMapper @Inject constructor() : Mapper<Statistic, StatisticDto> {
    override fun toDomain(item: StatisticDto, options: Map<String, Int>): Statistic {
        return Statistic(
            bicyclesSold = item.bicyclesSold,
            issuesLosses = item.issuesLosses,
            profitDirty = item.profitDirty,
            moneySpent = item.moneySpent,
            profitClear = item.profitDirty - (item.issuesLosses + item.moneySpent)
        )
    }

    override fun toData(item: Statistic, options: Map<String, Int>): StatisticDto {
        throw IllegalStateException("Mapping ${Statistic::javaClass} to ${StatisticDto::javaClass} is forbidden")
    }
}