package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleAllSpecs
import ru.nsu.fit.data.model.BicycleDto
import ru.nsu.fit.data.model.IssueDto
import ru.nsu.fit.domain.model.*
import javax.inject.Inject

class BicycleMapper @Inject constructor(
    private val issueMapper: Mapper<Issue, IssueDto>
) : Mapper<Bicycle, BicycleAllSpecs> {
    override fun toDomain(item: BicycleAllSpecs, options: Map<String, Int>): Bicycle {
        return Bicycle(
            item.bicycleDto.bikeId,
            item.bicycleDto.name,
            item.bicycleDto.purchasePrice,
            item.bicycleDto.sellingPrice,
            item.bicycleDto.description,
            item.bicycleDto.picture,
            Type(item.typeName),
            State(item.stateName),
            item.wheelSizeInches,
            Color(item.colorName),
            if (item.issueDtos.isEmpty()) emptyList() else item.issueDtos.map(issueMapper::toDomain)
        )
    }

    override fun toData(item: Bicycle, options: Map<String, Int>): BicycleAllSpecs {
        val typeId by options
        val stateId by options
        val wheelSizeId by options
        val colorId by options
        val bicycle = BicycleDto(
            item.id,
            item.name,
            item.purchasePrice,
            item.sellingPrice,
            item.description,
            item.picture,
            typeId,
            stateId,
            wheelSizeId, colorId
        )
        return BicycleAllSpecs(
            bicycle,
            item.type.typeName,
            item.color.colorName,
            item.state.description,
            item.wheelSize,
            item.issues.map(issueMapper::toData)
        )
    }
}