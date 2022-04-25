package ru.nsu.fit.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface ExternalStorageManager {
    suspend fun getImage(uri: Uri): Bitmap
}