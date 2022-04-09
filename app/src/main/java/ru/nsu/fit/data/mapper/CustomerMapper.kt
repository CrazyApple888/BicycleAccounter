package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.CustomerDto
import ru.nsu.fit.domain.model.Customer
import javax.inject.Inject

class CustomerMapper @Inject constructor() : Mapper<Customer, CustomerDto> {
    override fun toDomain(item: CustomerDto, options: Map<String, Int>): Customer {
        return Customer(name = item.name, phone = item.phone)
    }

    override fun toData(item: Customer, options: Map<String, Int>): CustomerDto {
        val customerId by options.withDefault { 0 }
        return CustomerDto(id = customerId, name = item.name, phone = item.phone)
    }
}