package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BaseDto
import ru.nsu.fit.domain.model.BaseModel

interface Mapper<DM : BaseModel, DT : BaseDto> {
    fun toDomain(item: DT, options: Map<String, Int> = emptyMap()): DM

    fun toData(item: DM, options: Map<String, Int> = emptyMap()): DT
}