package ru.nsu.fit.domain.repository

import ru.nsu.fit.domain.model.Issue
import ru.nsu.fit.domain.model.Result

interface IssueRepository {
    suspend fun addIssue(issue: Issue): Result<*>
    suspend fun attachIssueToBicycle(bikeId: Int, issueId: Int): Result<*>
}