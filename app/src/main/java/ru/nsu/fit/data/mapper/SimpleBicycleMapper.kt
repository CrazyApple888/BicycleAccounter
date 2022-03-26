package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleSimplified
import ru.nsu.fit.domain.model.SimpleBicycle
import javax.inject.Inject

class SimpleBicycleMapper @Inject constructor() : Mapper<SimpleBicycle, BicycleSimplified> {
    override fun toDomain(item: BicycleSimplified, options: Map<String, Int>): SimpleBicycle =
        SimpleBicycle(
            item.bikeId,
            item.name,
            item.sellingPrice,
            item.picture,
            item.wheelSize
        )

    override fun toData(item: SimpleBicycle, options: Map<String, Int>): BicycleSimplified =
        BicycleSimplified(
            item.id,
            item.name,
            item.sellingPrice,
            item.picture,
            item.wheelSize
        )
}