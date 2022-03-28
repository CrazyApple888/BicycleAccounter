package ru.nsu.fit.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.nsu.fit.data.dao.*
import ru.nsu.fit.data.model.*

class BicycleAccounterDatabaseTest {
    private lateinit var db: BicycleAccounterDatabase
    private lateinit var bicycleDao: BicycleDao
    private lateinit var colorDao: ColorDao
    private lateinit var wheelSizeDao: WheelSizeDao
    private lateinit var typeDao: BicycleTypeDao
    private lateinit var stateDao: BicycleStateDao


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BicycleAccounterDatabase::class.java).build()
        bicycleDao = db.bicycleDao()
        colorDao = db.colorDao()
        wheelSizeDao = db.wheelSizeDao()
        typeDao = db.bicycleTypeDao()
        stateDao = db.bicycleStateDao()

        //populating database
        populate()
    }


    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun bicycleDao() = runBlocking(Dispatchers.Main) {
        val list = bicycleDao.selectBicycleAll().take(1).toList().first()
        assertTrue(list.isNotEmpty())
        val bicycle = list.first()

        assertEquals("bebra", bicycle.name)
        assertEquals(2000, bicycle.purchasePrice)
        assertEquals(5000, bicycle.sellingPrice)
        assertEquals("description", bicycle.description)
        assertEquals(20, bicycle.typeIdRef)
        assertEquals(20, bicycle.stateIdRef)
        assertEquals(20, bicycle.wheelSizeIdRef)
        assertEquals(20, bicycle.colorIdRef)
    }

    private fun populate() {
        val colors = listOf(
            ColorDto(colorId = 20, colorName = "yellow"),
            ColorDto(colorName = "black"),
            ColorDto(colorName = "brown"),
            ColorDto(colorName = "green"),
            ColorDto(colorName = "red")
        )

        val types = listOf(
            BicycleType(typeId = 20, typeName = "mountain"),
            BicycleType(typeName = "hybrid"),
            BicycleType(typeName = "road"),
            BicycleType(typeName = "folding"),
            BicycleType(typeName = "adventure")
        )

        val sizes = listOf(
            WheelSize(sizeInches = 20.0),
            WheelSize(sizeInches = 24.0),
            WheelSize(sizeInches = 26.0),
            WheelSize(sizeId = 20, sizeInches = 27.5),
            WheelSize(sizeInches = 29.0)
        )

        val states = listOf(
            BicycleState(stateId = 20, stateName = "sold"),
            BicycleState(stateName = "selling"),
            BicycleState(stateName = "in service"),
            BicycleState(stateName = "returned"),
            BicycleState(stateName = "bebra")
        )

        val bicycles = listOf(
            BicycleDto(
                name = "bebra",
                purchasePrice = 2000,
                sellingPrice = 5000,
                description = "description",
                picture = null,
                typeIdRef = 20,
                stateIdRef = 20,
                wheelSizeIdRef = 20,
                colorIdRef = 20
            )
        )
        runBlocking {
            colors.map { colorDao.insertColorItem(it) }
            types.map { typeDao.insertBicycleTypeItem(it) }
            sizes.map { wheelSizeDao.insertWheelSizeItem(it) }
            states.map { stateDao.insertBicycleStateItem(it) }
            bicycles.map { bicycleDao.insertBicycleItem(it) }
        }

    }

}
