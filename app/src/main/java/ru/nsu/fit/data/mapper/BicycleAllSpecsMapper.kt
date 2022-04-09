package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.*
import ru.nsu.fit.domain.model.*
import javax.inject.Inject

class BicycleMapper @Inject constructor(
    private val issueMapper: Mapper<Issue, IssueDto>,
    private val colorMapper: Mapper<Color, ColorDto>,
    private val stateDtoMapper: Mapper<State, BicycleStateDto>,
    private val typeDtoMapper: Mapper<Type, BicycleTypeDto>,
    private val wheelSizeDtoMapper: Mapper<WheelSize, WheelSizeDto>
) : Mapper<Bicycle, BicycleAllSpecsDto> {
    override fun toDomain(item: BicycleAllSpecsDto, options: Map<String, Int>): Bicycle {
        return Bicycle(
            id = item.bicycleDto.bikeId,
            name = item.bicycleDto.name,
            purchasePrice = item.bicycleDto.purchasePrice,
            sellingPrice = item.bicycleDto.sellingPrice,
            description = item.bicycleDto.description,
            picture = item.bicycleDto.picture,
            type = typeDtoMapper.toDomain(item.typeDto),
            state = stateDtoMapper.toDomain(item.stateDto),
            wheelSize = wheelSizeDtoMapper.toDomain(item.wheelSizeDto),
            color = colorMapper.toDomain(item.color),
            issues = item.issueDtos.map(issueMapper::toDomain)
        )
    }

    override fun toData(item: Bicycle, options: Map<String, Int>): BicycleAllSpecsDto {
        val typeId by options
        val stateId by options
        val sizeId by options
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
            sizeId, colorId
        )
        return BicycleAllSpecsDto(
            bicycle,
            typeDtoMapper.toData(item.type, mapOf("typeId" to typeId)),
            colorMapper.toData(item.color, mapOf("colorId" to colorId)),
            stateDtoMapper.toData(item.state, mapOf("stateId" to stateId)),
            wheelSizeDtoMapper.toData(item.wheelSize, mapOf("sizeId" to sizeId)),
            item.issues.map(issueMapper::toData)
        )
    }
}