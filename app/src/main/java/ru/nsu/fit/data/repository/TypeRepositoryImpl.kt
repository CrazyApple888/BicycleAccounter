package ru.nsu.fit.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.BicycleTypeDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.BicycleTypeDto
import ru.nsu.fit.data.model.TransactionFailure
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Type
import ru.nsu.fit.domain.repository.TypeRepository
import javax.inject.Inject

class TypeRepositoryImpl @Inject constructor(
    private val typeDao: BicycleTypeDao,
    private val typeMapper: Mapper<Type, BicycleTypeDto>
) : TypeRepository {
    override suspend fun getTypeAll(): Flow<Result<List<Type>>> = withContext(Dispatchers.IO) {
        typeDao.selectBicycleTypeAll().map { types ->
            if (types.isEmpty()) {
                Result.Failure(message = "Failed to load bicycle states")
            } else {
                Result.Success(result = types.map(typeMapper::toDomain))
            }
        }
    }

    override suspend fun insertTypeItem(type: Type): Result<Int> = withContext(Dispatchers.IO) {
        val id =
            typeDao.selectIdByName(type.typeName) ?: typeDao.insertBicycleTypeItem(
                typeMapper.toData(type)
            ).toInt()
        if (TransactionFailure.ALREADY_EXISTS != id && TransactionFailure.TRANSACTION_REJECTED != id) {
            Result.Success(result = id)
        } else {
            Result.Failure(
                message = "Unable to insert new bicycle type or retrieve id of existing"
            )
        }
    }
}