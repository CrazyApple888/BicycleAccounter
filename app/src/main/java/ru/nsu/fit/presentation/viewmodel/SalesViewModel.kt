package ru.nsu.fit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Loggable
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.Sale
import ru.nsu.fit.domain.usecase.GetAllSalesUseCase
import javax.inject.Inject

class SalesViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase
) : ViewModel(), Loggable {

    private val _sales = MutableSharedFlow<List<Sale>>()
    val sales: SharedFlow<List<Sale>> get() = _sales.asSharedFlow()


    fun loadSales() {
        viewModelScope.launch {
            getAllSalesUseCase().apply {
                collect { result ->
                    when (result) {
                        is Result.Success -> _sales.emit(result.result ?: emptyList())
                        else -> Log.i(loggingTag, "Failure result is unexpected: $result")
                    }
                }
            }
        }
    }
}