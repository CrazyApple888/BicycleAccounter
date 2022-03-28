package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.ColorDto
import ru.nsu.fit.domain.model.Color
import javax.inject.Inject

class ColorMapper @Inject constructor() : Mapper<Color, ColorDto> {
    override fun toDomain(item: ColorDto, options: Map<String, Int>): Color {
        return Color(colorName = item.colorName)
    }

    override fun toData(item: Color, options: Map<String, Int>): ColorDto {
        val colorId: Int? by options
        return ColorDto(
            colorId = colorId ?: 0,
            colorName = item.colorName
        )
    }
}