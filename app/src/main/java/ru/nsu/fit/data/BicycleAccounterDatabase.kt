package ru.nsu.fit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.nsu.fit.data.dao.*
import ru.nsu.fit.data.model.*
import ru.nsu.fit.data.typeconverter.CalendarTypeConverter
import ru.nsu.fit.data.typeconverter.ImageTypeConverter
import ru.nsu.fit.data.typeconverter.StateTypeConverter
import ru.nsu.fit.data.model.SoldBicycleDto

@Database(
    entities = [BicycleDto::class,
        BicycleIssueXref::class,
        BicycleStateDto::class,
        BicycleTypeDto::class,
        ColorDto::class,
        CustomerDto::class,
        IssueDto::class,
        SaleDto::class,
        WheelSizeDto::class],
    views = [SoldBicycleDto::class],
    version = 4
)

@TypeConverters(ImageTypeConverter::class, StateTypeConverter::class, CalendarTypeConverter::class)
abstract class BicycleAccounterDatabase : RoomDatabase() {
    abstract fun bicycleDao(): BicycleDao
    abstract fun bicycleIssueXrefDao(): BicycleIssueXrefDao
    abstract fun bicycleStateDao(): BicycleStateDao
    abstract fun bicycleTypeDao(): BicycleTypeDao
    abstract fun colorDao(): ColorDao
    abstract fun customerDao(): CustomerDao
    abstract fun issuesDao(): IssuesDao
    abstract fun salesDao(): SalesDao
    abstract fun wheelSizeDao(): WheelSizeDao
}