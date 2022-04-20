package ru.nsu.fit.data.model

import java.util.*

data class CustomerSimplifiedDto(
    val id: Int = 0,
    val name: String,
    val phone: String,
    val lastTrade: Calendar?
) : BaseDto()