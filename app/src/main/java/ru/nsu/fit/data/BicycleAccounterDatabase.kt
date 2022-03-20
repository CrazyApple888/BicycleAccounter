package ru.nsu.fit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.nsu.fit.data.dao.*
import ru.nsu.fit.data.model.*
import ru.nsu.fit.data.model.Bicycle
import ru.nsu.fit.data.typeconverter.ImageTypeConverter

@Database(
    entities = [Bicycle::class,
        BicycleIssueXref::class,
        BicycleState::class,
        BicycleType::class,
        Color::class,
        Customer::class,
        Issue::class,
        Sale::class,
        WheelSize::class],
    version = 2
)
@TypeConverters(ImageTypeConverter::class)
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