package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.usecase.GetBicycleUseCase
import javax.inject.Inject

class DetailedBicycleViewModel @Inject constructor(
    private val getBicycleUseCase: GetBicycleUseCase
) : ViewModel() {

    private var currentBicycle: Bicycle? = null
    private val _bicycle = MutableSharedFlow<Bicycle>()
    val bicycle: SharedFlow<Bicycle> get() = _bicycle

    private val _error = MutableSharedFlow<Unit>()
    val error: SharedFlow<Unit> = _error.asSharedFlow()

    private val _sellCall = MutableSharedFlow<Bicycle>()
    val sellCall: SharedFlow<Bicycle> = _sellCall.asSharedFlow()

    fun loadBicycle(id: Int) {
        viewModelScope.launch {
            getBicycleUseCase(id).collect { result ->
                when (result) {
                    is Result.Success -> result.result?.let { bike ->
                        _bicycle.emit(bike)
                        currentBicycle = bike
                    }
                    is Result.Failure -> _error.emit(Unit)
                }
            }
        }
    }

    fun onSellButton() {
        viewModelScope.launch {
            //Unchecked because if screen loaded then currentBicycle is not null
            _sellCall.emit(currentBicycle!!)
        }
    }
}