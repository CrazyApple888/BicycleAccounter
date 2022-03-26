package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.ColorDto
import ru.nsu.fit.domain.model.Color

class ColorMapper: Mapper<Color, ColorDto> {
    override fun toDomain(item: ColorDto, options: Map<String, Int>): Color {
        TODO("Not yet implemented")
    }

    override fun toData(item: Color, options: Map<String, Int>): ColorDto {
        TODO("Not yet implemented")
    }
}