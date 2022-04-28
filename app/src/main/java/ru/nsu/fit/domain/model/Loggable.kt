package ru.nsu.fit.domain.model

interface Loggable {
    val loggingTag: String
        get() = this.javaClass.name
}