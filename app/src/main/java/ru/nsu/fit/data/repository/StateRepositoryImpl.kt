package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleStateDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.BicycleStateDto
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.State
import ru.nsu.fit.domain.repository.StateRepository
import javax.inject.Inject

class StateRepositoryImpl @Inject constructor(
    private val stateDao: BicycleStateDao,
    private val stateMapper: Mapper<State, BicycleStateDto>
) : StateRepository {
    override suspend fun getStateAll(): Flow<Result<List<State>>> = withContext(Dispatchers.IO) {
        stateDao.selectBicycleStateAll().map { states ->
            if (states.isEmpty()) {
                Result.Failure(message = "Failed to load bicycle states")
            } else {
                Result.Success(result = states.map(stateMapper::toDomain))
            }
        }
    }

    override suspend fun insertStateItem(state: State): Result<Int> = withContext(Dispatchers.IO){
        val id = stateDao.insertBicycleStateItem(stateMapper.toData(state, mapOf("stateId" to 0)))
            .toInt()
        if (0 != id) {
            Result.Success(result = id)
        } else {
            Result.Failure(message = null)
        }
    }


}