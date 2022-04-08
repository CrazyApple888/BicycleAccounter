package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleTypeDto
import ru.nsu.fit.domain.model.Type
import javax.inject.Inject

class TypeMapper @Inject constructor() : Mapper<Type, BicycleTypeDto> {
    override fun toDomain(item: BicycleTypeDto, options: Map<String, Int>): Type {
        return Type(typeName = item.typeName)
    }

    override fun toData(item: Type, options: Map<String, Int>): BicycleTypeDto {
        val typeId by options.withDefault { 0 }
        return BicycleTypeDto(
            typeId = typeId,
            typeName = item.typeName
        )
    }
}