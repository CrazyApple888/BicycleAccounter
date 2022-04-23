package ru.nsu.fit.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.domain.model.SaleDetailed
import ru.nsu.fit.domain.repository.SalesManager
import ru.nsu.fit.domain.model.Result
import javax.inject.Inject

class GetSaleDetailedUseCase @Inject constructor(
    private val salesManager: SalesManager
) {
    suspend operator fun invoke(bikeId: Int): Flow<Result<SaleDetailed>> =
        salesManager.getSale(bikeId)
}