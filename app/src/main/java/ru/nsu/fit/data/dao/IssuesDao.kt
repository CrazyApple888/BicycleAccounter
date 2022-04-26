package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.BicycleIssueXref
import ru.nsu.fit.data.model.IssueDto

@Dao
interface IssuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssueItem(issueDto: IssueDto): Long

    @Query("SELECT * FROM issues")
    fun selectIssueAll(): Flow<List<IssueDto>>

    @Query("SELECT DISTINCT * FROM issues WHERE issues.issueId = :id")
    fun selectIssueById(id: Int): Flow<IssueDto?>

    @Insert
    fun insertBicycleToIssueRef(ref: BicycleIssueXref): Long
}