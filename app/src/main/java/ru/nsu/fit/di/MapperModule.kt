package ru.nsu.fit.di

import dagger.Binds
import dagger.Module
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.mapper.SimpleBicycleMapper
import ru.nsu.fit.data.model.BicycleSimplified
import ru.nsu.fit.domain.model.SimpleBicycle

@Module
interface MapperModule {

    @Binds
    fun bindSimpleBicycleMapper(mapper: SimpleBicycleMapper): Mapper<SimpleBicycle, BicycleSimplified>

}