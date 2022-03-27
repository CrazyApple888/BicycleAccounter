package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleType
import ru.nsu.fit.domain.model.Type
import javax.inject.Inject

class TypeMapper @Inject constructor() : Mapper<Type, BicycleType> {
    override fun toDomain(item: BicycleType, options: Map<String, Int>): Type {
        return Type(typeName = item.typeName)
    }

    override fun toData(item: Type, options: Map<String, Int>): BicycleType {
        val typeId by options
        return BicycleType(
            typeId = typeId,
            typeName = item.typeName
        )
    }
}