package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.usecase.GetAllBicyclesUseCase
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val getAllBicyclesUseCase: GetAllBicyclesUseCase
) : ViewModel() {

    private val _bicycles = MutableSharedFlow<List<SimpleBicycle>>()
    val bicycles: SharedFlow<List<SimpleBicycle>> get() = _bicycles.asSharedFlow()

    private val _error = MutableSharedFlow<Unit>()
    val error: SharedFlow<Unit> = _error.asSharedFlow()


    fun loadBicycles() {
        viewModelScope.launch {
            getAllBicyclesUseCase().collect { result ->
                when (result) {
                    is Result.Success -> _bicycles.emit(result.result ?: emptyList())
                    is Result.Failure -> _error.emit(Unit)
                }
            }
        }
    }

}