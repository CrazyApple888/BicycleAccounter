package ru.nsu.fit.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.nsu.fit.data.model.BicycleIssueXref

@Dao
interface BicycleIssueXrefDao {
    @Query("SELECT * FROM bicycle_issue_xref")
    fun selectBicycleIssueXrefAll(): Flow<List<BicycleIssueXref>>

    @Query("SELECT * FROM bicycle_issue_xref WHERE bicycle_issue_xref.bikeIdRef = :id")
    fun selectBicycleIssueXrefByBikeId(id: Int): Flow<List<BicycleIssueXref>>

    @Query("SELECT * FROM bicycle_issue_xref WHERE bicycle_issue_xref.issueIdRef = :id")
    fun selectBicycleIssueXrefByIssueId(id: Int): Flow<List<BicycleIssueXref>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertXref(item: BicycleIssueXref)

    suspend fun insertXrefItem(bikeId: Int, issueId: Int) {
        insertXref(BicycleIssueXref(bikeIdRef = bikeId, issueIdRef = issueId))
    }

    @Delete
    suspend fun deleteXref(item: BicycleIssueXref)

    suspend fun deleteXrefItem(bikeIdRef: Int, issueIdRef: Int) {
        deleteXref(BicycleIssueXref(bikeIdRef, issueIdRef))
    }
}