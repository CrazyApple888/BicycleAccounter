package ru.nsu.fit.data.mapper

interface Mapper<DM, DT> {
    fun toDomain(item: DT): DM

    fun toData(item: DM): DT
}