package ru.nsu.fit.data.typeconverter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ImageTypeConverter {

    @TypeConverter
    fun toBitmap(array: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }


    @TypeConverter
    fun toByteArray(bitmap: Bitmap): ByteArray {
        val outStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        return outStream.toByteArray()
    }

}