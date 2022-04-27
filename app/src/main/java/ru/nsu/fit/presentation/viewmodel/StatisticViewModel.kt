package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Loggable
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Statistic
import ru.nsu.fit.domain.usecase.GetStatisticUseCase
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
    private val getStatsUseCase: GetStatisticUseCase
) : ViewModel(), Loggable {
    private val _statistics = MutableSharedFlow<Statistic>(replay = 1)
    val statistics: SharedFlow<Statistic> get() = _statistics.asSharedFlow()

    private val _messages = MutableSharedFlow<Result<String>>(replay = 1)
    val messages: SharedFlow<Result<String>> get() = _messages.asSharedFlow()

    init {
        loadStats()
    }

    fun loadStats() {
        viewModelScope.launch {
            when (val result = getStatsUseCase()) {
                is Result.Success -> _statistics.emit(result.result!!)
                is Result.Failure -> _messages.emit(Result.Failure(message = "Failed to load stats"))
            }
        }
    }
}