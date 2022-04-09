package ru.nsu.fit.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.*
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
    fun expectSameIdsWhenSameNamesInserted() = runBlocking {
        val id1 = colorDao.insertColorItem(ColorDto(colorName = "yellow"))
        val id2 = colorDao.insertColorItem(ColorDto(colorName = "yellow"))
        assertEquals(id1, id2)
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

    //я точно не помню зачем написал этот тест и придумал название от балды
    @Test
    fun expectSameIdAndRowid() = runBlocking(Dispatchers.Main) {
        val id = colorDao.insertColorItem(ColorDto(colorName = "teal")).toInt()
        val colorItem = colorDao.selectColorById(id).take(1).toList().first()
        assertNotNull(colorItem)

        assertEquals(id, colorItem?.colorId)
        assertEquals("teal", colorItem?.colorName)
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
            BicycleTypeDto(typeId = 20, typeName = "mountain"),
            BicycleTypeDto(typeName = "hybrid"),
            BicycleTypeDto(typeName = "road"),
            BicycleTypeDto(typeName = "folding"),
            BicycleTypeDto(typeName = "adventure")
        )

        val sizes = listOf(
            WheelSizeDto(sizeInches = 20.0),
            WheelSizeDto(sizeInches = 24.0),
            WheelSizeDto(sizeInches = 26.0),
            WheelSizeDto(sizeId = 20, sizeInches = 27.5),
            WheelSizeDto(sizeInches = 29.0)
        )

        val states = listOf(
            BicycleStateDto(stateId = 20, stateName = "sold"),
            BicycleStateDto(stateName = "selling"),
            BicycleStateDto(stateName = "in service"),
            BicycleStateDto(stateName = "returned"),
            BicycleStateDto(stateName = "bebra")
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
