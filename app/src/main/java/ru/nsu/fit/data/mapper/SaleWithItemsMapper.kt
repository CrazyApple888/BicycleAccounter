package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.*
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.domain.model.SimpleBicycle
import javax.inject.Inject

class SaleWithItemsMapper @Inject constructor(
    private val bicycleMapper: Mapper<SimpleBicycle, SoldBicycleDto>,
    private val customerMapper: Mapper<Customer, CustomerDto>
): Mapper<Sale, SaleWithItemsDto> {
    override fun toDomain(item: SaleWithItemsDto, options: Map<String, Int>): Sale {
        return Sale(
            bicycle = bicycleMapper.toDomain(item.bicycle),
            saleDate = item.saleDto.saleDate,
            customer = customerMapper.toDomain(item.customer),
            finalCost = item.saleDto.finalCost
        )
    }

    override fun toData(item: Sale, options: Map<String, Int>): SaleWithItemsDto {
        throw IllegalStateException("Mapping ${Sale::javaClass} to ${SaleWithItemsDto::javaClass} is forbidden")
    }
}