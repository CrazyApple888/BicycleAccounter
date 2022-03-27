package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.*
import ru.nsu.fit.domain.model.*
import javax.inject.Inject

class BicycleMapper @Inject constructor(
    private val issueMapper: Mapper<Issue, IssueDto>,
    private val colorMapper: Mapper<Color, ColorDto>,
    private val stateMapper: Mapper<State, BicycleState>,
    private val typeMapper: Mapper<Type, BicycleType>
) : Mapper<Bicycle, BicycleAllSpecs> {
    override fun toDomain(item: BicycleAllSpecs, options: Map<String, Int>): Bicycle {
        return Bicycle(
            id = item.bicycleDto.bikeId,
            name = item.bicycleDto.name,
            purchasePrice = item.bicycleDto.purchasePrice,
            sellingPrice = item.bicycleDto.sellingPrice,
            description = item.bicycleDto.description,
            picture = item.bicycleDto.picture,
            type = typeMapper.toDomain(item.type),
            state = stateMapper.toDomain(item.state),
            wheelSize = item.wheelSize,
            color = colorMapper.toDomain(item.color),
            issues = item.issueDtos.map(issueMapper::toDomain)
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
            typeMapper.toData(item.type, mapOf("typeId" to typeId)),
            colorMapper.toData(item.color, mapOf("colorId" to colorId)),
            stateMapper.toData(item.state, mapOf("stateId" to stateId)),
            item.wheelSize,
            item.issues.map(issueMapper::toData)
        )
    }
}