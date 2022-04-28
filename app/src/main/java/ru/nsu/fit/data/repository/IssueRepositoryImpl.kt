package ru.nsu.fit.data.repository

import ru.nsu.fit.data.dao.IssuesDao
import ru.nsu.fit.data.mapper.Mapper
import ru.nsu.fit.data.model.BicycleIssueXref
import ru.nsu.fit.data.model.IssueDto
import ru.nsu.fit.data.model.TransactionStatus
import ru.nsu.fit.domain.model.Issue
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.repository.IssueRepository
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(
    private val issuesDao: IssuesDao,
    private val mapper: Mapper<Issue, IssueDto>
) : IssueRepository {
    override suspend fun addIssue(issue: Issue): Result<Int> {
        val id = issuesDao.insertIssueItem(mapper.toData(issue))
        return if (id == TransactionStatus.TRANSACTION_REJECTED.toLong()) {
            Result.Failure(message = "Failed to insert issue item $issue")
        } else {
            Result.Success(id.toInt())
        }
    }

    override suspend fun attachIssueToBicycle(bikeId: Int, issueId: Int): Result<*> {
        val xref = BicycleIssueXref(bikeId, issueId)
        return if (issuesDao.insertBicycleToIssueRef(xref) == TransactionStatus.TRANSACTION_REJECTED.toLong()) {
            Result.Failure<Any>(message = "Failed to insert issue item $xref")
        } else {
            Result.Success<Any>()
        }
    }
}