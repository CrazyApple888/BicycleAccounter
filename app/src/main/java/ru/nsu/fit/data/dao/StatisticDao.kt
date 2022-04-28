package ru.nsu.fit.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface StatisticDao {
    @Query("SELECT DISTINCT SUM(finalCost) FROM sales")
    suspend fun getDirtyProfitAll(): Int?

    @Query(
        "SELECT DISTINCT SUM(issues.cost) " +
                "FROM sold_bicycles " +
                "INNER JOIN bicycle_issue_xref on bicycle_issue_xref.bikeIdRef = sold_bicycles.bikeId " +
                "INNER JOIN issues on issues.issueId = bicycle_issue_xref.issueIdRef "
    )
    suspend fun getIssuesLossesAll(): Int?

    @Query("SELECT DISTINCT SUM(purchasePrice) FROM sold_bicycles")
    suspend fun getMoneySpent(): Int?

    @Query("SELECT DISTINCT COUNT(*) FROM sold_bicycles")
    suspend fun getSoldBicyclesCount(): Int?
}