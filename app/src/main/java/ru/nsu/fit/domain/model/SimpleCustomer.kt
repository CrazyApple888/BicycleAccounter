package ru.nsu.fit.domain.model

import java.util.*

data class SimpleCustomer(
    val id: Int,
    val name: String,
    val phone: String,
    val lastTrade: Calendar?
): BaseModel()
