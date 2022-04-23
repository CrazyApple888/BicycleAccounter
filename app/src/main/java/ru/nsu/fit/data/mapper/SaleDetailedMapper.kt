package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.SaleDetailedDto
import ru.nsu.fit.domain.model.SaleDetailed
import javax.inject.Inject

class SaleDetailedMapper @Inject constructor(): Mapper<SaleDetailed, SaleDetailedDto> {
    override fun toDomain(item: SaleDetailedDto, options: Map<String, Int>): SaleDetailed {
        val cost = item.issueDtos.sumOf { it.cost }.toLong()
        return SaleDetailed(
            customerName = item.customer.name,
            bicycleName = item.bicycle.name,
            additionalInfo = item.issueDtos.map { it.description }.joinToString(separator = "\n"),
            sellPrice = item.saleDto.finalCost,
            buyPrice = item.bicycle.purchasePrice.toLong(),
            cost = cost,
            totalCost = item.saleDto.finalCost - item.bicycle.purchasePrice - cost
        )
    }

    override fun toData(item: SaleDetailed, options: Map<String, Int>): SaleDetailedDto {
        throw IllegalStateException("Mapping ${SaleDetailed::javaClass} to ${SaleDetailedDto::javaClass} is forbidden")
    }
}