package ru.nsu.fit.domain.usecase

import android.graphics.Bitmap
import android.net.Uri
import ru.nsu.fit.domain.repository.ExternalStorageManager
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val externalStorageManager: ExternalStorageManager
) {
    suspend operator fun invoke(uri: Uri): Bitmap = externalStorageManager.getImage(uri)
}