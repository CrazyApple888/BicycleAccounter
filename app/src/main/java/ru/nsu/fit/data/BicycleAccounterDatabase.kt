package ru.nsu.fit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nsu.fit.data.dao.BicycleDao
import ru.nsu.fit.data.dao.CustomerDao
import ru.nsu.fit.data.dao.SalesDao
import ru.nsu.fit.data.model.*
import ru.nsu.fit.data.model.Bicycle

@Database(
    entities = [Bicycle::class,
        BicycleIssueXref::class,
        BicycleState::class,
        BicycleType::class,
        Colors::class,
        Customer::class,
        Issue::class,
        Sales::class,
        WheelSize::class],
    version = 1
)
abstract class BicycleAccounterDatabase : RoomDatabase() {
    abstract fun bicycleDao(): BicycleDao
    abstract fun salesDao(): SalesDao
    abstract fun customerDao(): CustomerDao
}