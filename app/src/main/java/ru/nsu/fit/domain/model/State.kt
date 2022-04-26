package ru.nsu.fit.domain.model

data class State(
    val stateName: String
) : BaseModel() {
    val isSold: Boolean
        get() = stateName == "Продан"
    val isSelling: Boolean
        get() = stateName == "Продается"
    val isNotSelling: Boolean
        get() = !isSelling
}