package ru.nsu.fit.data.repository

import android.content.ContentResolver
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import ru.nsu.fit.domain.repository.ExternalStorageManager
import java.io.File
import javax.inject.Inject


class ExternalStorageManagerImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : ExternalStorageManager {

    override suspend fun getImage(uri: Uri): Bitmap {
        return queryImage(uri)
    }

    private fun queryImage(uri: Uri): Bitmap {
        val docId = DocumentsContract.getDocumentId(uri)
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = "_id=?"
        val selectionArgs = arrayOf(
            docId.split(":")[1]
        )

        val imageFile = File(getDataColumn(contentUri, selection, selectionArgs))
        val filePath: String = imageFile.path
        return BitmapFactory.decodeFile(filePath)
    }

    private fun getDataColumn(
        uri: Uri,
        selection: String,
        selectionArgs: Array<String>
    ): String {
        var cursor: Cursor? = null
        val column = MediaStore.Images.Media.DATA
        val projection = arrayOf(
            column
        )
        try {
            cursor = contentResolver.query(
                uri, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return ""
    }
}