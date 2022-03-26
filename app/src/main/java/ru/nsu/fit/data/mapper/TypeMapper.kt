package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleType
import ru.nsu.fit.domain.model.Type

class TypeMapper: Mapper<Type, BicycleType> {
    override fun toDomain(item: BicycleType, options: Map<String, Int>): Type {
        TODO("Not yet implemented")
    }

    override fun toData(item: Type, options: Map<String, Int>): BicycleType {
        TODO("Not yet implemented")
    }
}