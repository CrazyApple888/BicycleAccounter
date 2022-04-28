package ru.nsu.fit.data.model

data class StatisticDto(
    val bicyclesSold: Int,
    val moneySpent: Int,
    val issuesLosses: Int,
    val profitDirty: Int
) : BaseDto()