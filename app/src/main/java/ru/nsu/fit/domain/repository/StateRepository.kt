package ru.nsu.fit.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.State

interface StateRepository {
    suspend fun getStateAll(): Flow<Result<List<State>>>
    suspend fun insertStateItem(state: State): Result<Int>
}