package ru.nsu.fit.data.typeconverter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ImageTypeConverter {

    @TypeConverter
    fun toBitmap(array: ByteArray?): Bitmap? {
        array?.let {
            return BitmapFactory.decodeByteArray(array, 0, array.size)
        }
        return null
    }


    @TypeConverter
    fun toByteArray(bitmap: Bitmap?): ByteArray? {
        bitmap?.let {
            val outStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            return outStream.toByteArray()
        }
        return null
    }

}