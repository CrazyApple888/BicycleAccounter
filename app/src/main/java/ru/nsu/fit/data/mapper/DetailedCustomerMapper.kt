package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.CustomerDetailedDto
import ru.nsu.fit.data.model.SoldBicycleDto
import ru.nsu.fit.domain.model.DetailedCustomer
import ru.nsu.fit.domain.model.SimpleBicycle
import javax.inject.Inject

class DetailedCustomerMapper @Inject constructor(
    private val bicycleMapper: Mapper<SimpleBicycle, SoldBicycleDto>
) : Mapper<DetailedCustomer, CustomerDetailedDto> {
    override fun toDomain(item: CustomerDetailedDto, options: Map<String, Int>): DetailedCustomer {
        return DetailedCustomer(
            id = item.customer.id,
            name = item.customer.name,
            phone = item.customer.phone,
            bicycles = item.bikes.map { bicycleMapper.toDomain(it) }
        )
    }

    override fun toData(item: DetailedCustomer, options: Map<String, Int>): CustomerDetailedDto {
        throw IllegalStateException("You are trying to call stub")
    }
}