package ru.nsu.fit.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.nsu.fit.data.BicycleAccounterDatabase
import ru.nsu.fit.data.dao.*
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
        ).createFromAsset("database/database_sample.db").build()
    }

    @Singleton
    @Provides
    fun provideDatabase(): BicycleAccounterDatabase = database

    @Singleton
    @Provides
    fun provideBicycleDao(): BicycleDao = database.bicycleDao()

    @Singleton
    @Provides
    fun provideBicycleIssueXrefDao(): BicycleIssueXrefDao = database.bicycleIssueXrefDao()

    @Singleton
    @Provides
    fun provideBicycleStateDao(): BicycleStateDao = database.bicycleStateDao()

    @Singleton
    @Provides
    fun provideBicycleTypeDao(): BicycleTypeDao = database.bicycleTypeDao()

    @Singleton
    @Provides
    fun provideColorDao(): ColorDao = database.colorDao()

    @Singleton
    @Provides
    fun provideCustomerDao(): CustomerDao = database.customerDao()

    @Singleton
    @Provides
    fun provideStatisticDao(): StatisticDao = database.statisticDao()

    @Singleton
    @Provides
    fun provideIssuesDao(): IssuesDao = database.issuesDao()

    @Singleton
    @Provides
    fun provideSalesDao(): SalesDao = database.salesDao()

    @Singleton
    @Provides
    fun provideWheelSizeDao(): WheelSizeDao = database.wheelSizeDao()
}