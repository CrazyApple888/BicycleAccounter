package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleSimplifiedDto
import ru.nsu.fit.data.model.WheelSizeDto
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.model.WheelSize
import javax.inject.Inject

class SimpleBicycleMapper @Inject constructor(
    private val wheelSizeMapper: Mapper<WheelSize, WheelSizeDto>
) : Mapper<SimpleBicycle, BicycleSimplifiedDto> {
    override fun toDomain(item: BicycleSimplifiedDto, options: Map<String, Int>): SimpleBicycle =
        SimpleBicycle(
            item.bikeId,
            item.name,
            item.sellingPrice,
            item.picture,
            wheelSizeMapper.toDomain(item.wheelSize)
        )

    override fun toData(item: SimpleBicycle, options: Map<String, Int>): BicycleSimplifiedDto {
        val sizeId by options.withDefault { 0 }
        return BicycleSimplifiedDto(
            item.id,
            item.name,
            item.sellingPrice,
            item.picture,
            wheelSizeMapper.toData(item.wheelSize, mapOf("sizeId" to sizeId))
        )
    }
}