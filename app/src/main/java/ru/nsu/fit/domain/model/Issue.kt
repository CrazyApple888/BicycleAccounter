package ru.nsu.fit.domain.model

data class Issue(
    val issueId: Int = 0,
    val description: String,
    val cost: Int
) : BaseModel()