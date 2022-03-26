package ru.nsu.fit.data.mapper

interface Mapper<DM, DT> {
    fun toDomain(item: DT, options: Map<String, Int> = emptyMap()): DM

    fun toData(item: DM, options: Map<String, Int> = emptyMap()): DT
}