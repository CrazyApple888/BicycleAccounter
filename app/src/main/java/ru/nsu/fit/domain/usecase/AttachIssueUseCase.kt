package ru.nsu.fit.domain.usecase

import ru.nsu.fit.domain.model.Issue
import ru.nsu.fit.domain.repository.BicycleRepository
import javax.inject.Inject


class AttachIssueUseCase @Inject constructor(
    private val repository: BicycleRepository
) {
    suspend operator fun invoke(bicycleId: Int, issue: Issue) =
        repository.addIssueToBicycle(bicycleId, issue)
}
