package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleSimplifiedDto
import ru.nsu.fit.data.model.CustomerDto
import ru.nsu.fit.data.model.SaleWithItemsDto
import ru.nsu.fit.domain.model.Customer
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.domain.model.SimpleBicycle
import javax.inject.Inject

class SaleWithItemsMapper @Inject constructor(
    private val bicycleAllSpecsMapper: Mapper<SimpleBicycle, BicycleSimplifiedDto>,
    private val customerMapper: Mapper<Customer, CustomerDto>
): Mapper<Sale, SaleWithItemsDto> {
    override fun toDomain(item: SaleWithItemsDto, options: Map<String, Int>): Sale {
        return Sale(
            bicycle = bicycleAllSpecsMapper.toDomain(item.bicycle),
            date = item.date,
            warrantyEndDate = item.warrantyEndDate,
            customer = customerMapper.toDomain(item.customer),
            finalCost = item.finalCost
        )
    }

    override fun toData(item: Sale, options: Map<String, Int>): SaleWithItemsDto {
        TODO("Unused")
    }
}