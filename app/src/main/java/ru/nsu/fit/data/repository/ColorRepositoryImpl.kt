package ru.nsu.fit.data.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.fit.data.dao.ColorDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.ColorDto
import ru.nsu.fit.data.model.TransactionFailure
import ru.nsu.fit.domain.model.Color
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.ColorRepository
import javax.inject.Inject

class ColorRepositoryImpl @Inject constructor(
    private val colorDao: ColorDao,
    private val colorMapper: Mapper<Color, ColorDto>
) : ColorRepository {
    override suspend fun getColorAll(): Flow<Result<List<Color>>> = withContext(Dispatchers.IO) {
        colorDao.selectColorAll().map { colors ->
            if (colors.isEmpty()) {
                Result.Failure(message = "Failed to load colors")
            } else {
                Result.Success(result = colors.map(colorMapper::toDomain))
            }
        }
    }

    override suspend fun insertColorItem(color: Color): Result<Int> = withContext(Dispatchers.IO) {
        val id =
            colorDao.selectIdByName(color.colorName) ?: colorDao.insertColorItem(
                colorMapper.toData(color)
            ).toInt()
        if (TransactionFailure.TRANSACTION_REJECTED != id) {
            Result.Success(result = id)
        } else {
            Result.Failure(
                message = "Unable to insert new color or retrieve id of existing"
            )
        }
    }
}