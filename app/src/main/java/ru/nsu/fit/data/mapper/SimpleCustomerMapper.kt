package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.CustomerSimplifiedDto
import ru.nsu.fit.domain.model.SimpleCustomer
import javax.inject.Inject

class SimpleCustomerMapper @Inject constructor() : Mapper<SimpleCustomer, CustomerSimplifiedDto> {
    override fun toDomain(item: CustomerSimplifiedDto, options: Map<String, Int>): SimpleCustomer {
        return SimpleCustomer(
            id = item.id,
            name = item.name,
            phone = item.phone,
            lastTrade = item.lastTrade
        )
    }

    override fun toData(item: SimpleCustomer, options: Map<String, Int>): CustomerSimplifiedDto {
        return CustomerSimplifiedDto(
            id = item.id,
            name = item.name,
            phone = item.phone,
            lastTrade = item.lastTrade
        )
    }
}