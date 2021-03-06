package ru.nsu.fit.di

import dagger.Binds
import dagger.Module
import ru.nsu.fit.data.mapper.*
import ru.nsu.fit.data.model.*
import ru.nsu.fit.domain.model.*

@Module
interface MapperModule {
    @Binds
    fun bindSimpleBicycleMapper(mapper: SimpleBicycleMapper): Mapper<SimpleBicycle, BicycleSimplifiedDto>

    @Binds
    fun bicycleMapper(mapper: BicycleAllSpecsMapper): Mapper<Bicycle, BicycleAllSpecsDto>

    @Binds
    fun bindTypeMapper(mapper: TypeMapper): Mapper<Type, BicycleTypeDto>

    @Binds
    fun bindStatisticMapper(mapper: StatisticMapper): Mapper<Statistic, StatisticDto>

    @Binds
    fun bindColorMapper(mapper: ColorMapper): Mapper<Color, ColorDto>

    @Binds
    fun bindStateMapper(mapper: StateMapper): Mapper<State, BicycleStateDto>

    @Binds
    fun bindIssueMapper(mapper: IssueMapper): Mapper<Issue, IssueDto>

    @Binds
    fun bindWheelSizeMapper(mapper: WheelSizeMapper): Mapper<WheelSize, WheelSizeDto>

    @Binds
    fun bindCustomerMapper(mapper: CustomerMapper): Mapper<Customer, CustomerDto>

    @Binds
    fun bindSoldCustomerMapper(mapper: SoldBicycleMapper): Mapper<SimpleBicycle, SoldBicycleDto>

    @Binds
    fun bindDetailedCustomerMapper(mapper: DetailedCustomerMapper): Mapper<DetailedCustomer, CustomerDetailedDto>

    @Binds
    fun bindSimpleCustomerMapper(mapper: SimpleCustomerMapper): Mapper<SimpleCustomer, CustomerSimplifiedDto>

    @Binds
    fun bindSaleMapper(mapper: SaleWithItemsMapper): Mapper<Sale, SaleWithItemsDto>

    @Binds
    fun bindSaleDetailedMapper(mapper: SaleDetailedMapper): Mapper<SaleDetailed, SaleDetailedDto>
}