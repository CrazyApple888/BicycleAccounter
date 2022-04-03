package ru.nsu.fit.di

import dagger.Binds
import dagger.Module
import ru.nsu.fit.data.repository.BicycleRepositoryImpl
import ru.nsu.fit.data.repository.SalesRepositoryImpl
import ru.nsu.fit.domain.repository.BicycleRepository
import ru.nsu.fit.domain.repository.SalesRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindBicycleRepository(repo: BicycleRepositoryImpl): BicycleRepository

    @Binds
    fun bindSalesRepository(repositoryImpl: SalesRepositoryImpl): SalesRepository

}