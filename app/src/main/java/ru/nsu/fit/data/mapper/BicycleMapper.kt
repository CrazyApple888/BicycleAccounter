package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleSimplified
import ru.nsu.fit.domain.model.Bicycle

class BicycleMapper: Mapper<Bicycle, BicycleSimplified> {
    override fun toDomain(item: BicycleSimplified): Bicycle {
        TODO("Not yet implemented")
    }

    override fun toData(item: Bicycle): BicycleSimplified {
        TODO("Not yet implemented")
    }
}