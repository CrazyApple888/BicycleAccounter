package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.Issue

@Dao
interface IssuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssueItem(issue: Issue)

    suspend fun insertIssueItem(cost: Int, description: String?) {
        insertIssueItem(Issue(issueId = 0, cost = cost, description = description))
    }

    @Query("SELECT * FROM issues")
    fun selectIssueAll(): Flow<List<Issue>>

    @Query("SELECT DISTINCT * FROM issues WHERE issues.issueId = :id")
    fun selectIssueById(id: Int): Flow<Issue>
}