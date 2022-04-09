package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Color
import ru.nsu.fit.domain.model.Result

interface ColorRepository {
    suspend fun getColorAll(): Flow<Result<List<Color>>>
    suspend fun insertColorItem(color: Color): Result<Int>
}