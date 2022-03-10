package ru.nsu.fit.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.nsu.fit.data.BicycleAccounterDatabase
import ru.nsu.fit.data.dao.BicycleDao
import javax.inject.Singleton

@Module
class DataModule(
    private val context: Context
) {
    private val database: BicycleAccounterDatabase by lazy {
        Room.databaseBuilder(
            context,
            BicycleAccounterDatabase::class.java,
            "BicycleAccounterDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDatabase(): BicycleAccounterDatabase = database

    @Singleton
    @Provides
    fun provideBicycleDao(): BicycleDao = database.bicycleDao()

    @Singleton
    @Provides
    fun provideSalesDao() = database.salesDao()

    @Singleton
    @Provides
    fun provideCustomerDao() = database.customerDao()
}