package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.domain.repository.SalesManager
import javax.inject.Inject

class GetAllSalesUseCase @Inject constructor(
    private val salesManager: SalesManager
) {
    suspend operator fun invoke(): Flow<Result<List<Sale>>> = salesManager.getAllSales()
}