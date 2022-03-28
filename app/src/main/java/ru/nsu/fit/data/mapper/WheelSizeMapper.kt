package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.WheelSizeDto
import ru.nsu.fit.domain.model.WheelSize
import javax.inject.Inject

class WheelSizeMapper @Inject constructor() : Mapper<WheelSize, WheelSizeDto> {
    override fun toDomain(item: WheelSizeDto, options: Map<String, Int>): WheelSize {
        return WheelSize(diameter = item.sizeInches)
    }

    override fun toData(item: WheelSize, options: Map<String, Int>): WheelSizeDto {
        val sizeId by options
        return WheelSizeDto(sizeId = sizeId, sizeInches = item.diameter)
    }
}