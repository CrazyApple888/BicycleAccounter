package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.SoldBicycleDto
import ru.nsu.fit.data.model.WheelSizeDto
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.model.WheelSize
import javax.inject.Inject

class SoldBicycleMapper @Inject constructor(
    private val wheelSizeMapper: Mapper<WheelSize, WheelSizeDto>
) : Mapper<SimpleBicycle, SoldBicycleDto> {
    override fun toDomain(item: SoldBicycleDto, options: Map<String, Int>): SimpleBicycle {
        return SimpleBicycle(
            id = item.bikeId,
            name = item.name,
            sellingPrice = item.finalCost.toInt(),
            picture = item.picture,
            wheelSize = wheelSizeMapper.toDomain(item.wheelSize)
        )
    }

    override fun toData(item: SimpleBicycle, options: Map<String, Int>): SoldBicycleDto {
        throw IllegalStateException("Mapping ${SimpleBicycle::javaClass} to ${SoldBicycleDto::javaClass} is forbidden")
    }
}