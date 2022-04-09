package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Type

interface TypeRepository {
    suspend fun getTypeAll(): Flow<Result<List<Type>>>
    suspend fun insertTypeItem(type: Type): Result<Int>
}