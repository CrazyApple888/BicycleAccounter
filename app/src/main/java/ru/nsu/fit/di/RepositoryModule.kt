package ru.nsu.fit.di

import dagger.Binds
import dagger.Module
import ru.nsu.fit.data.repository.BicycleRepositoryImpl
import ru.nsu.fit.domain.repository.BicycleRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindBicycleRepository(repo: BicycleRepositoryImpl): BicycleRepository

}