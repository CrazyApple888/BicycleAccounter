package ru.nsu.fit.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, V> Flow<List<T>>.mapModels(mapper: (T) -> V): Flow<List<V>> {
    return this.map { list -> list.map(mapper) }
}