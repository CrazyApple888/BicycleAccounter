package ru.nsu.fit.domain.model

import java.util.*

//TODO remove calendar stubs
data class Statistic(
    val startPeriod: Calendar = Calendar.getInstance(),
    val endPeriod: Calendar = Calendar.getInstance(),
    val bicyclesSold: Int,
    val moneySpent: Int,
    val issuesLosses: Int,
    val profitDirty: Int,
    val profitClear: Int
) : BaseModel()
